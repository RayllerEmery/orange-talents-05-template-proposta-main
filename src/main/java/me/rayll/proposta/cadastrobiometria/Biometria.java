package me.rayll.proposta.cadastrobiometria;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import me.rayll.proposta.cartao.Cartao;

@Entity
public class Biometria {

	@Id
	private String id = UUID.randomUUID().toString();

	@OneToOne
	private Cartao cartao;

	private String idBiometria = "";

	@Deprecated
	private Biometria() {
	}

	public Biometria(@NotEmpty Cartao cartao, @NotEmpty String idBiometria) {
		this.cartao = cartao;
		this.idBiometria = idBiometria;
	}

	public BiometriaDTO toDTO() {
		BiometriaDTO dto = new BiometriaDTO(idBiometria, cartao.getId());
		dto.setId(this.id);
		return dto;
	}

}
