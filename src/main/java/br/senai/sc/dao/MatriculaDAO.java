package br.senai.sc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.senai.sc.model.Curso;
import br.senai.sc.model.Matricula;
import br.senai.sc.model.Pessoa;

public class MatriculaDAO {

	@PersistenceContext(unitName = "prova-java")
	private EntityManager em;

	public void createOrEdit(Matricula matricula) {
		em.merge(matricula);
	}

	public void remove(Matricula matricula) {
		Matricula matriculaManaged = em.merge(matricula);
		em.remove(matriculaManaged);
	}

	@SuppressWarnings("unchecked")
	public List<Matricula> findAll() {
		Query query = em.createQuery("SELECT m FROM Matricula m");
		return (List<Matricula>) query.getResultList();
	}

	public List<Matricula> findByCurso(Curso curso) {
		TypedQuery<Matricula> query = em.createQuery("SELECT m FROM Matricula m WHERE m.curso = :curso",
				Matricula.class);
		query.setParameter("curso", curso);
		return (List<Matricula>) query.getResultList();
	}
	
	public List<Matricula> findByCpf(Pessoa pessoa) {
		TypedQuery<Matricula> query = em.createQuery("SELECT m FROM Matricula m WHERE m.pessoa = :pessoa",
				Matricula.class);
		query.setParameter("pessoa", pessoa);
		return (List<Matricula>) query.getResultList();
	}

	public Matricula findByPessoaAndCurso(Pessoa pessoa, Curso curso) {
		TypedQuery<Matricula> query = em.createQuery(
				"SELECT m FROM Matricula m WHERE m.pessoa = :pessoa AND m.curso = :curso", Matricula.class);
		query.setParameter("pessoa", pessoa);
		query.setParameter("curso", curso);
		return query.getSingleResult();
	}
}
