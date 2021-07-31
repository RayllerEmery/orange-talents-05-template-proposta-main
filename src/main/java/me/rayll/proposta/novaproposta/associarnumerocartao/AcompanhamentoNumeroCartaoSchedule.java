package me.rayll.proposta.novaproposta.associarnumerocartao;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.rayll.proposta.cartao.Cartao;
import me.rayll.proposta.novaproposta.EstadoProposta;
import me.rayll.proposta.novaproposta.Proposta;
import me.rayll.proposta.novaproposta.PropostaRepository;

@EnableScheduling
@Component
public class AcompanhamentoNumeroCartaoSchedule {

	@Autowired
	private PropostaRepository propostaRepository;
	@Autowired
	private RetornoNumeroDeCartaoFeign retornoCartaoFeign;

	@Async
	@Scheduled(fixedDelay = 5000L, initialDelay = 10000L)
	@Transactional
	public ResponseEntity<Void> executaBuscaDePropostasSemNumeroDeCartao() throws JsonMappingException, JsonProcessingException {
		//buscar NovasPropostas elegíveis e sem numero de cartão no repository
		Set<Proposta> listaBuscada = buscarPropostasElegiveis();
		
		if(listaBuscada.size() > 0) {
			try {
				for (Proposta novaProposta : listaBuscada) {
					//busca na api externa o numero do cartão e retorna um json em string
					String s = retornoCartaoFeign.criarCartaoString(novaProposta.toDTO().getId().toString());
					
					ObjectMapper mapper = new ObjectMapper();
					String idCartaoRecebido = mapper.readValue(s, JsonNode.class).get("id").asText();
					
						Cartao cartao = new Cartao(idCartaoRecebido, novaProposta);
						novaProposta.setCartao(cartao);						
						propostaRepository.save(novaProposta);
								
				}
			} catch (ResponseStatusException e) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
			}
		}
		return ResponseEntity.ok().build();
	}
	
	private Set<Proposta> buscarPropostasElegiveis() {
		return propostaRepository.findByEstadoPropostaLikeAndCartaoNull(EstadoProposta.ELEGIVEL);
	}
}
