package br.senai.sc.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;


@Entity
@Table(name="pessoa")
public class Pessoa {
	
	@Id	
	@Column(name="cpf")
	@NotEmpty(message="O campo cpf não pode ser vazio")
	@CPF
	private String cpf;
	
	@Column(name="nome")
	@Pattern(regexp = "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$", message = "O nome deve iniciar com letra maiúscula em cada palavra")
	private String nomeCompleto;
	@Column
	@NotNull(message="O campo data de nascimento não pode ser vazio")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	public Pessoa() {
		
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	  @Override
	    public String toString() {
	        return "Pessoa [nomeCompleto=" + nomeCompleto + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento + "]";
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(cpf);
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        Pessoa other = (Pessoa) obj;
	        return Objects.equals(cpf, other.cpf);
	    }
	}
