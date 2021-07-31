package me.rayll.proposta.cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import me.rayll.proposta.novaproposta.Proposta;

@Entity
public class Cartao {
	
	@Id
	private String id;
	@OneToOne(mappedBy = "cartao")
	private Proposta proposta;
	private StatusCartao statusCartao = StatusCartao.ATIVO;
	
	@Deprecated
	private Cartao() {}

	public Cartao(String idCartaoRecebido, Proposta novaProposta) {
		this.id = idCartaoRecebido;
		this.proposta = novaProposta;
	}
	
	public String getId() {
		return this.id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	public void trocarStatusCartao(StatusCartao statusCartao) {
		this.statusCartao = statusCartao;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cartao other = (Cartao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public StatusCartao getStatusCartao() {
		return this.statusCartao;
	}

	public Proposta getProposta() {
		return proposta;
	}

}
