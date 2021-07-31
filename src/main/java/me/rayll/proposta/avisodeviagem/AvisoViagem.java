package me.rayll.proposta.avisodeviagem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import me.rayll.proposta.cartao.Cartao;

@Entity
public class AvisoViagem {

	@Id
	private String id = UUID.randomUUID().toString();
	@OneToOne
	private Cartao cartao;
	private @NotEmpty String destino;
	private LocalDate termino;
	private LocalDateTime instante;
	private String ipCliente;
	private String userClient;
	
	@Deprecated
	private AvisoViagem() {}

	public AvisoViagem(Cartao cartao, @NotEmpty String destino, LocalDate termino, LocalDateTime instante,
			String ipCliente, String userClient) {
				this.cartao = cartao;
				this.destino = destino;
				this.termino = termino;
				this.instante = instante;
				this.ipCliente = ipCliente;
				this.userClient = userClient;
		
	}
	
	public AvisoViagemDTO toDTO() {
		String terminoString = this.termino.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		AvisoViagemDTO dto = new AvisoViagemDTO(
				this.destino,
				terminoString,
				this.instante,
				this.ipCliente,
				this.userClient);
		dto.setIdCartao(this.cartao.getId());
		return dto;
	}
	
}
