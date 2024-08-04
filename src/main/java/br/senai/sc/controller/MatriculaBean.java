package br.senai.sc.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.senai.sc.model.Curso;
import br.senai.sc.model.Matricula;
import br.senai.sc.model.Pessoa;
import br.senai.sc.service.CursoService;
import br.senai.sc.service.MatriculaService;
import br.senai.sc.service.PessoaService;
import br.senai.sc.utils.Funcoes;
import br.senai.sc.utils.Messages;

@Named
@ViewScoped
public class MatriculaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private MatriculaService matriculaService;
	@Inject
	private CursoService cursoService;
	@Inject
	private PessoaService pessoaService;

	@Inject
	private Messages messages;

	private Matricula matricula;

	private List<Matricula> matriculas;
	private List<Curso> cursos;
	private List<Pessoa> pessoas;
	private Long cursoId;
	private String pessoaCpf;
	private String termoBusca;

	@PostConstruct
	public void init() {
		matricula = new Matricula();
		pessoas = pessoaService.listarPessoas();
		cursos = cursoService.listarCursos();
		matriculas = matriculaService.listarMatriculas();
	}

	public void prepareEdit(Matricula matricula) {
		this.matricula = matricula;
	}

	public String formatarData(Date data) {
		Funcoes funcao = new Funcoes();
		LocalDate dataConvertida = funcao.converterData(data);
		if (dataConvertida != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return dataConvertida.format(formatter);
		}
		return "";
	}

	public void excluirMatricula(Matricula matricula) {
		Matricula matriculaManaged = matriculaService.acharPorPkComposta(matricula.getPessoa(), matricula.getCurso());
		if (matriculaManaged != null) {
			Curso curso = matricula.getCurso();
			int vagasOcupadas = curso.getVagasOcupadas();
			int novasVagas = vagasOcupadas - 1;
			try {
				matriculaService.remove(matricula);
				curso.setVagasOcupadas(0 - 1);

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao deletar matricula", e.getMessage()));
				return;
			}
			curso.setVagasOcupadas(novasVagas);
			cursoService.criarOuEditarCurso(curso);
			matriculas = matriculaService.listarMatriculas();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Matricula não encontrada para exclusão", null));
			return;
		}
	}

	public void criarMatricula() {
		try {

			Funcoes funcao = new Funcoes();

			matricula.setPessoa(pessoaService.findByCpf(pessoaCpf));
			matricula.setCurso(cursoService.findById(cursoId));

			LocalDate dataAtual = LocalDate.now();
			LocalDate dataNascConvertida = funcao.converterData(matricula.getPessoa().getDataNascimento());
			int idade = Period.between(dataNascConvertida, dataAtual).getYears();
			if (idade < matricula.getCurso().getIdadeMinimaAluno()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Aluno muito novo", null));
				FacesContext.getCurrentInstance().getExternalContext().redirect("matriculas.xhtml");
				return;
			}

			LocalDate dataMatriculaConvertida = funcao.converterData(matricula.getDataInicio());
			LocalDate dataInicioCursoConvertida = funcao.converterData(matricula.getCurso().getDataInicio());
			LocalDate dataFimCursoConvertida = funcao.converterData(matricula.getCurso().getDataTermino());

			if (dataMatriculaConvertida.isAfter(dataInicioCursoConvertida)
					|| dataMatriculaConvertida.isAfter(dataFimCursoConvertida)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Data de início ou de término é incompatível", null));
				FacesContext.getCurrentInstance().getExternalContext().redirect("matriculas.xhtml");
				return;
			}

			if ((matricula.getCurso().getTotalVagas()) < matricula.getCurso().getVagasOcupadas() + 1) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Curso já esta com todas as vagas ocupadas", null));
				FacesContext.getCurrentInstance().getExternalContext().redirect("matriculas.xhtml");
				return;
			}

			matriculaService.createOrEdit(matricula);
			;
			messages.info("Pessoas criada com sucesso");
			matriculas = matriculaService.listarMatriculas();
			Curso curso = matricula.getCurso();
			int totalPessoas = curso.getVagasOcupadas();
			curso.setVagasOcupadas(totalPessoas + 1);
			cursoService.criarOuEditarCurso(curso);
			matricula = new Matricula();

			FacesContext.getCurrentInstance().getExternalContext().redirect("matriculas.xhtml");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao criar matriculas", e.getMessage()));
		}
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public String getTermoBusca() {
		return termoBusca;
	}

	public void setTermoBusca(String termoBusca) {
		this.termoBusca = termoBusca;
	}

	public Long getCursoId() {
		return cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

	public String getPessoaCpf() {
		return pessoaCpf;
	}

	public void setPessoaCpf(String pessoaCpf) {
		this.pessoaCpf = pessoaCpf;
	}

}
