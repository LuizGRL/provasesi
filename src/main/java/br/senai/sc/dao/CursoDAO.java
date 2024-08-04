package br.senai.sc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.senai.sc.model.Curso;

public class CursoDAO {
	
	@PersistenceContext(unitName = "prova-java")
    private EntityManager em;
    
    public void createOrEdit(Curso cruso) {
        em.merge(cruso);
    }
    
    public void remove(Curso curso) {
        Curso cursoManaged = em.merge(curso);
    	em.remove(cursoManaged);
    }
    
    public Curso findById(long id) {
        return em.find(Curso.class, id);
    }
    
    @SuppressWarnings("unchecked")
	public List<Curso> findAllCursos(){
    	Query query = em.createQuery("SELECT c FROM Curso c");
    	return (List<Curso>) query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Curso> findByNome(String nome) {
        Query query = em.createQuery("SELECT c FROM Curso c WHERE LOWER(c.nome) LIKE LOWER(:nome)");
        query.setParameter("nome", "%" + nome + "%");
        return (List<Curso>) query.getResultList();
    }

}
