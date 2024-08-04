package br.senai.sc.utils;

import java.io.Serializable;
import java.util.Objects;

public class MatriculaId implements Serializable{
	
	private Long curso;
	private String pessoa;
	
	public MatriculaId() {
		
	}
	public MatriculaId(Long curso, String pessoa) {
		super();
		this.curso = curso;
		this.pessoa = pessoa;
	}
	public Long getCurso() {
		return curso;
	}
	public void setCurso(Long curso) {
		this.curso = curso;
	}
	public String getpessoa() {
		return pessoa;
	}
	public void setpessoa(String pessoa) {
		this.pessoa = pessoa;
	}
	@Override
	public int hashCode() {
		return Objects.hash(pessoa, curso);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatriculaId other = (MatriculaId) obj;
		return Objects.equals(pessoa, other.pessoa) && Objects.equals(curso, other.curso);
	}

}
