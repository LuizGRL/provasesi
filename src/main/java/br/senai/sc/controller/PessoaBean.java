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
import br.senai.sc.model.Pessoa;
import br.senai.sc.service.PessoaService;
import br.senai.sc.utils.Funcoes;
import br.senai.sc.utils.Messages;

@Named
@ViewScoped
public class PessoaBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PessoaService pessoaService;

	@Inject
	private Messages messages;

	private Pessoa pessoa;

	private List<Pessoa> pessoas;

	private String termoBusca;

	@PostConstruct
	public void init() {
		pessoa = new Pessoa();
		pessoas = pessoaService.listarPessoas();
	}

	public void prepareEdit(Pessoa pessoa) {
		this.pessoa = pessoa;
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

	public void criarPessoa() {
		try {
			
			LocalDate dataAtual = LocalDate.now();
			Funcoes funcao = new Funcoes();
			LocalDate dataNascimentoConvertida = funcao.converterData(pessoa.getDataNascimento());
			if(dataNascimentoConvertida.isAfter(dataAtual)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"A data de nascimento não pode ser depois que a data atual", null));
				pessoa = new Pessoa();
				FacesContext.getCurrentInstance().getExternalContext().redirect("alunos.xhtml");
				return;
			}
			pessoaService.criarOuEditarPessoa(pessoa);
			messages.info("Pessoas criada com sucesso");
			pessoas = pessoaService.listarPessoas();
			pessoa = new Pessoa();
			FacesContext.getCurrentInstance().getExternalContext().redirect("alunos.xhtml");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao criar curso", e.getMessage()));
		}
	}

	public void buscarPessoa() {
		pessoas = pessoaService.buscarPessoaPorCpf(termoBusca);
		if (pessoas.isEmpty()) {
			messages.info("Consulta não retornou dados");
		}
	}

	public void excluirPessoa(Pessoa pessoa) {
		Pessoa pessoaManaged = pessoaService.findByCpf((pessoa.getCpf()));
		if (pessoaManaged != null) {
			pessoaService.excluir(pessoaManaged);
			pessoas = pessoaService.listarPessoas();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Curso não encontrado para exclusão", null));
		}
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public String getTermoBusca() {
		return termoBusca;
	}

	public void setTermoBusca(String termoBusca) {
		this.termoBusca = termoBusca;
	}


}
