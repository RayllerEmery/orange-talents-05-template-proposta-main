package me.rayll.proposta.associacaocarteira;

import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

public class AssociarCarteriaDTO {
	
	private String id;
	
	
	private String email;
	
	private String resultado;
	@NotNull
	private CarteiraAssociacaoEnum carteira;

	public AssociarCarteriaDTO(String email, CarteiraAssociacaoEnum carteira) {
		this.email = email;
		this.carteira = carteira;
	}

	public AssociarCarteriaDTO(CarteiraAssociacaoEnum carteira) {
		this.carteira = carteira;
	}
	
	@Deprecated
	public AssociarCarteriaDTO() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CarteiraAssociacaoEnum getCarteira() {
		return carteira;
	}

	public void setCarteira(CarteiraAssociacaoEnum carteira) {
		this.carteira = carteira;
	}

	public AssociacaoCarteira ToModel() {
		AssociacaoCarteira model = new AssociacaoCarteira(id, this.carteira, resultado);
		return model;
	}

	public void setId(String idAssociacao) {
		this.id = idAssociacao;
		
	}

	public void setResultado(String  resultado) {
		this.resultado = resultado;
		
	}
	
}
