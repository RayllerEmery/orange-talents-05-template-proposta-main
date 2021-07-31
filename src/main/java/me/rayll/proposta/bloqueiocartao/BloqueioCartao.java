package me.rayll.proposta.bloqueiocartao;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import me.rayll.proposta.cartao.Cartao;
import me.rayll.proposta.cartao.StatusCartao;

@Entity
public class BloqueioCartao {

	@Id
	private String id = UUID.randomUUID().toString();
	@OneToOne
	private Cartao cartao;
	private LocalDateTime instante;
	private @NotEmpty String ipCliente;
	private @NotEmpty String userAgent;

	private BloqueioCartao() {}
	
	public BloqueioCartao(Cartao cartao, LocalDateTime instante, @NotEmpty String ipCliente, @NotEmpty String userAgent) {
		this.cartao = cartao;
		this.instante = instante;
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
	}
	
	public BloqueioCartaoDTO toDTO() {
		BloqueioCartaoDTO dto = new BloqueioCartaoDTO(ipCliente, userAgent);
		dto.setInstante(this.instante);
		dto.setid(this.cartao.getId());
		dto.setid(this.id);
		
		return dto;
	}
	
}
