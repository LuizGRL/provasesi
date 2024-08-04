package br.senai.sc.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import br.senai.sc.service.CursoService;
import br.senai.sc.service.MatriculaService;
import br.senai.sc.utils.Funcoes;
import br.senai.sc.utils.Messages;

@Named
@ViewScoped
public class CursoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CursoService cursoService;

	@Inject
	private MatriculaService matriculaService;

	@Inject
	private Messages messages;

	private Curso curso;

	private List<Curso> cursos;

	private String termoBusca;

	@PostConstruct
	public void init() {
		curso = new Curso();
		cursos = cursoService.listarCursos();
	}

	public void prepareEdit(Curso curso) {
		this.curso = curso;
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

	public void criarCurso() {
		try {
			
			Funcoes funcao = new Funcoes();
			LocalDate dataInicioConvetida = funcao.converterData(curso.getDataInicio());
			LocalDate dataTerminoConvertida = funcao.converterData(curso.getDataTermino());
			LocalDate dataAtual = LocalDate.now();

			if (dataInicioConvetida.isAfter(dataTerminoConvertida)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Data início não pode ser depois da data de término", null));
				curso = new Curso();
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
				return;
			}
			if (dataTerminoConvertida.isBefore(dataAtual)) {
				
				FacesContext context = FacesContext.getCurrentInstance();
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"A data de término de um curso não pode ser antes que a data atual", null));
				curso = new Curso();
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
				return;
			}
			cursoService.criarOuEditarCurso(curso);
			messages.info("Curso criado com sucesso");
			cursos = cursoService.listarCursos();
			curso = new Curso();
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao criar curso", e.getMessage()));
		}
	}

	public void buscarCursos() {
		cursos = cursoService.buscarCursosPorNome(termoBusca);
		if (cursos.isEmpty()) {
			messages.info("Consulta não retornou dados");
		}
	}

	public void excluirCurso(Curso curso) {
		Curso cursoManaged = cursoService.findById(curso.getId());
		if (cursoManaged != null) {
			List<Matricula> matriculasCurso = matriculaService.listarPorCurso(curso);
			System.out.println(matriculasCurso.size());
			if (matriculasCurso.size() >= 1) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não é possivel excluir esse curso, pois ele esta associado a uma matrícula ", null));
				return;
			}
			cursoService.excluir(cursoManaged);
			cursos = cursoService.listarCursos();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Curso não encontrado para exclusão", null));
		}
	}

	public Curso getCurso() {
		return curso;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public String getTermoBusca() {
		return termoBusca;
	}

	public void setTermoBusca(String termoBusca) {
		this.termoBusca = termoBusca;
	}

}
