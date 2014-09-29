package org.talesolutions.cep;

import java.io.Serializable;

/**
 * Representa um CEP
 * 
 * @author Fábio Franco Uechi
  */
public class CEP implements Serializable {

	private static final long serialVersionUID = -3046561560949012411L;

	private final String numero;
	private final String logradouro;
	private final String bairro;
	private final String localidade;
	private final String uf;

	public CEP(String num, String logradouro, String bairro, String localidade,
			String uf) {
		this.numero = num;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.localidade = localidade;
		this.uf = uf;
	}

	public String getNumero() {
		return numero;
	}
	
	public String getLogradouro() {
		return logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public String getUf() {
		return uf;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result
				+ ((localidade == null) ? 0 : localidade.hashCode());
		result = prime * result
				+ ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CEP other = (CEP) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (localidade == null) {
			if (other.localidade != null)
				return false;
		} else if (!localidade.equals(other.localidade))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CEP [numero=" + numero + ", logradouro=" + logradouro
				+ ", bairro=" + bairro + ", localidade=" + localidade + ", uf="
				+ uf + "]";
	}
	
}