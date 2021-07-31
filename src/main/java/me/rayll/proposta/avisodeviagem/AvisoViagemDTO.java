package me.rayll.proposta.avisodeviagem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import me.rayll.proposta.cartao.Cartao;

public class AvisoViagemDTO {
	
	private String idCartao;
	@NotEmpty
	private String destino;
	@NotNull
	private String termino;
	
	private LocalDateTime instante = LocalDateTime.now(ZoneId.systemDefault());
	
	private String ipCliente = "";
	
	private String userClient = "";
	
	@Deprecated
	private AvisoViagemDTO() {}

	public AvisoViagemDTO(@NotEmpty String destino, String termino, LocalDateTime instante, String ipCliente,
			String userClient) {
		this.destino = destino;
		this.termino = termino;
		this.instante = instante;
		this.ipCliente = ipCliente;
		this.userClient = userClient;
	}

	public String getIdCartao() {
		return idCartao;
	}

	public String getDestino() {
		return destino;
	}

	public String getTermino() {
		return termino;
	}

	public LocalDateTime getInstante() {
		return instante;
	}

	public String getIpCliente() {
		return ipCliente;
	}

	public String getUserClient() {
		return userClient;
	}

	public void setIdCartao(String idCartao) {
		this.idCartao = idCartao;
	}
	public AvisoViagem toModel(Cartao c) {
		
		LocalDate terminoToDate = LocalDate.parse(termino, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		return new AvisoViagem(
				c,
				this.destino,
				terminoToDate,
				this.instante,
				this.ipCliente,
				this.userClient);
	}

}
