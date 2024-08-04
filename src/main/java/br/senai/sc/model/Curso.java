package br.senai.sc.model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="curso")
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	@NotEmpty(message="O campo nome não pode ser vazio")
	private String nome;
	@Column
	@NotNull(message="O campo toal devagas não pode ser vazio")
	@Min(value = 1, message= "O campo total de vagas precisa ser maior que zero")
	private int totalVagas;
	@Column
	private int vagasOcupadas;
	@Column
	@NotNull(message="O campo de data início não pode ser vazio")
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	@Column
	@NotNull(message="O campo de data término não pode ser vazio")
	@Temporal(TemporalType.DATE)
	private Date dataTermino;
	@Column
	@NotNull(message="O campo de idade não pode ser vazio")
	@Min(value = 1, message= "O campo de idade mínima precisa ser maior que zero")
	private int idadeMinimaAluno;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTotalVagas() {
		return totalVagas;
	}

	public void setTotalVagas(int totalVagas) {
		this.totalVagas = totalVagas;
	}

	public int getVagasOcupadas() {
		return vagasOcupadas;
	}

	public void setVagasOcupadas(int vagasOcupadas) {
		this.vagasOcupadas = vagasOcupadas;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public int getIdadeMinimaAluno() {
		return idadeMinimaAluno;
	}

	public void setIdadeMinimaAluno(int idadeMinimaAluno) {
		this.idadeMinimaAluno = idadeMinimaAluno;
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nome=" + nome + ", totalVagas=" + totalVagas + ", vagasOcupadas=" + vagasOcupadas
				+ ", dataInicio=" + dataInicio + ", dataTermino=" + dataTermino + ", idadeMinimaAluno="
				+ idadeMinimaAluno + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		return id == other.id;
	}
}
