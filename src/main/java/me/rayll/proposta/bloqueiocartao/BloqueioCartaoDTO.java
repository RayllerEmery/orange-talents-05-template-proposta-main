package me.rayll.proposta.bloqueiocartao;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.constraints.NotEmpty;

import me.rayll.proposta.cartao.Cartao;

public class BloqueioCartaoDTO {
	
	private String idCartao;
	
	private LocalDateTime instante = LocalDateTime.now(ZoneId.systemDefault());
	@NotEmpty
	private String ipCliente;
	@NotEmpty 
	private String userAgent;

	@Deprecated
	private BloqueioCartaoDTO() {}
	
	public BloqueioCartaoDTO(String ipCliente, String userAgent) {
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
	}
	
	public BloqueioCartaoDTO(LocalDateTime instante, String ipCliente, String userAgent) {
		this.instante = instante;
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
	}

	public String getIdCartao() {
		return idCartao;
	}

	public LocalDateTime getInstante() {
		return instante;
	}

	public String getIpCliente() {
		return ipCliente;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setInstante(LocalDateTime instante2) {
		this.instante = instante2;
	}

	public void setid(String id) {
		this.idCartao = id;
	}
	
	public BloqueioCartao toModel(Cartao cartao) {
		BloqueioCartao bloqueioCarto = new BloqueioCartao(cartao, this.instante, this.ipCliente, this.userAgent);
		return bloqueioCarto;
		
	}
	
}
