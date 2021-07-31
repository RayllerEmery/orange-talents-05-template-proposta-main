package me.rayll.proposta.novaproposta.consultadedados;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Interface anotada com Feign para fazer a consulta de maneira mais fácil em um sistemas externo.
 * A implementação é semelhante a de um endpoint.
 * A notação precisa de um nome e a url de consulta.
 */

@FeignClient(name = "propostaAprovacao", url = "${aprovacao_proposta_url}")
public interface AprovacaoPropostaFeign {
	
	@PostMapping
	public PropostaAprovacao getAprovacao(@RequestBody PropostaAprovacao propostaAprovacao);
}
