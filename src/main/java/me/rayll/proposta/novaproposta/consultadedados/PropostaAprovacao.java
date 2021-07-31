package me.rayll.proposta.novaproposta.consultadedados;

import javax.validation.constraints.NotEmpty;

import me.rayll.proposta.novaproposta.EstadoProposta;

public class PropostaAprovacao {
	@NotEmpty
	private String documento;
	
	private String nome;
	
	private Long idProposta;
	
	private String resultadoSolicitacao;
	
	private String numeroCartao;
	
	@Deprecated
	private PropostaAprovacao() {}

	public PropostaAprovacao(@NotEmpty String documento, String nome, Long idProposta, String resultadoSolicitacao) {
		
		this.documento = documento;
		this.nome = nome;
		this.idProposta = idProposta;
		this.resultadoSolicitacao = resultadoSolicitacao;
	}

	public PropostaAprovacao(@NotEmpty String documento, String nome, Long idProposta) {
		this.documento = documento;
		this.nome = nome;
		this.idProposta = idProposta;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public Long getIdProposta() {
		return idProposta;
	}

	public String getResultadoSolicitacao() {
		return resultadoSolicitacao;
	}
	
	public EstadoProposta estadoProposta() {
		return this.resultadoSolicitacao == "COM_RESTRICAO" ? EstadoProposta.NAO_ELEGIVEL : EstadoProposta.ELEGIVEL;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}
	
}
