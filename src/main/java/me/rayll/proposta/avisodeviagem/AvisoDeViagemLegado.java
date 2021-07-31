package me.rayll.proposta.avisodeviagem;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

public class AvisoDeViagemLegado {
	
	@NotEmpty
	private String destino;
	@NotNull
	private String validoAte;

	public AvisoDeViagemLegado(String destino, String validoAte) {
		
		this.destino = destino;
		this.validoAte = validoAte;
	}

	public String getDestino() {
		return destino;
	}

	public String getValidoAte() {
		return validoAte;
	}
	
	
	
}
