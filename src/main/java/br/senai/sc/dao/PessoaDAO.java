package br.senai.sc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.senai.sc.model.Pessoa;

public class PessoaDAO {
	
	@PersistenceContext(unitName = "prova-java")
    private EntityManager em;
    
    public void createOrEdit(Pessoa pessoa) {
        em.merge(pessoa);
    }
    
    public void remove(Pessoa pessoa) {
        Pessoa pessoaManaged = em.merge(pessoa);
    	em.remove(pessoaManaged);
    }
    
    public Pessoa findByCpfUnico(String cpf) {
        TypedQuery<Pessoa> query = em.createQuery("SELECT p FROM Pessoa p WHERE p.cpf = :cpf", Pessoa.class);
        query.setParameter("cpf", cpf);
        return query.getSingleResult();
    }
    @SuppressWarnings("unchecked")
	public List<Pessoa> findAllPessoas(){
    	Query query = em.createQuery("SELECT p FROM Pessoa p");
    	return (List<Pessoa>) query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Pessoa> findByCpf(String cpf) {
        Query query = em.createQuery("SELECT p FROM Pessoa p WHERE LOWER(p.cpf) LIKE LOWER(:cpf)");
        query.setParameter("cpf", "%" + cpf + "%");
        return (List<Pessoa>) query.getResultList();
    }

}
