package br.senai.sc.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.senai.sc.dao.PessoaDAO;
import br.senai.sc.model.Pessoa;



@Stateless
public class PessoaService {
	@Inject
	private PessoaDAO dao;

	public Pessoa findByCpf(String cpf) {
		return dao.findByCpfUnico(cpf);
	}

	public void excluir(Pessoa pessoa) {
		dao.remove(pessoa);
	}

	public void criarOuEditarPessoa(Pessoa pessoa) {
		dao.createOrEdit(pessoa);
	}

	public List<Pessoa> listarPessoas() {
		return dao.findAllPessoas();
	}

	public List<Pessoa> buscarPessoaPorCpf(String cpf) {
		return dao.findByCpf(cpf);
	}

}
