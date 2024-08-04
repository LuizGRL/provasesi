package br.senai.sc.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.senai.sc.dao.CursoDAO;
import br.senai.sc.model.Curso;

@Stateless
public class CursoService {

	@Inject
	private CursoDAO dao;

	public Curso findById(long id) {
		return dao.findById(id);
	}

	public void excluir(Curso curso) {
		dao.remove(curso);
	}

	public void criarOuEditarCurso(Curso curso) {
		dao.createOrEdit(curso);
	}

	public List<Curso> listarCursos() {
		return dao.findAllCursos();
	}

	public List<Curso> buscarCursosPorNome(String nome) {
		return dao.findByNome(nome);
	}
}
