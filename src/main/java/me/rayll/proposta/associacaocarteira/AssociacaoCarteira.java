package me.rayll.proposta.associacaocarteira;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import me.rayll.proposta.novaproposta.Proposta;

@Entity
public class AssociacaoCarteira {

	@Id
	private String id;
	
	private CarteiraAssociacaoEnum carteira;
	
	private String resultado;
	
	@ManyToOne
	private Proposta proposta;

	@Deprecated
	private AssociacaoCarteira() {}
	
	public AssociacaoCarteira(String id, CarteiraAssociacaoEnum carteira, String resultado) {
		super();
		this.id = id;
		this.carteira = carteira;
		this.resultado = resultado;
	}
	
}
