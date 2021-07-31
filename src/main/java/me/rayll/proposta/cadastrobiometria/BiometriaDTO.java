package me.rayll.proposta.cadastrobiometria;

import javax.validation.constraints.NotEmpty;

import me.rayll.proposta.cartao.Cartao;

public class BiometriaDTO {
	
	private String id;
	@NotEmpty
	private String idBiometria;
	@NotEmpty
	private String numeroCartao;
	
	@Deprecated
	private BiometriaDTO() {
	}
	
	public BiometriaDTO(@NotEmpty String idBiometria, @NotEmpty String numeroCartao) {
		this.idBiometria = idBiometria;
		this.numeroCartao = numeroCartao;
	}

	public Biometria toModel(Cartao cartao) {
		return new Biometria(cartao, this.idBiometria);
	}

	public String getIdBiometria() {
		return idBiometria;
	}

	public void setIdBiometria(String idBiometria) {
		this.idBiometria = idBiometria;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
