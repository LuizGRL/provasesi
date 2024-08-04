package br.senai.sc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.senai.sc.utils.MatriculaId;

@Entity
@Table(name = "matricula")
@IdClass(MatriculaId.class)
public class Matricula implements Serializable {
	
	@Id
	@ManyToOne
	@JoinColumn(name="curso_id")
	private Curso curso;
	
	@Id
	@ManyToOne
	@JoinColumn(name="pessoa_cpf")
	private Pessoa pessoa;
	@NotNull(message="Campo data início não pode ser nulo")
	@Temporal(TemporalType.DATE)
	@Column(name="Data_inicio")
	private Date dataInicio;

	public Matricula() {
		
	}
	
	public Matricula(Curso curso, Pessoa pessoa, Date dataIncio) {
		super();
		this.curso = curso;
		this.pessoa = pessoa;
		this.dataInicio = dataIncio;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataIncio) {
		this.dataInicio = dataIncio;
	}

	@Override
	public String toString() {
		return "Matricula [curso=" + curso + ", pessoa=" + pessoa + ", dataIncio=" + dataInicio + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(curso, pessoa);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matricula other = (Matricula) obj;
		return Objects.equals(curso, other.curso) && Objects.equals(pessoa, other.pessoa);
	}

}
