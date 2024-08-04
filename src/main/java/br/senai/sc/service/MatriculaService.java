package br.senai.sc.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.senai.sc.dao.MatriculaDAO;
import br.senai.sc.model.Curso;
import br.senai.sc.model.Matricula;
import br.senai.sc.model.Pessoa;

@Stateless
public class MatriculaService {
	@Inject
	public MatriculaDAO dao;
	
	public void createOrEdit(Matricula matricula) {
		dao.createOrEdit(matricula);
	}
	public void remove(Matricula matriucla) {
		dao.remove(matriucla);
	}
	
	public List<Matricula> listarMatriculas(){
		return dao.findAll();
	}
	
	public Matricula acharPorPkComposta(Pessoa pessoa, Curso curso) {
		return dao.findByPessoaAndCurso(pessoa, curso);
	}
	
	public List<Matricula> listarPorCurso(Curso curso){
		return dao.findByCurso(curso);
	} 


}
