package me.rayll.proposta.bloqueiocartao;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import me.rayll.proposta.cartao.Cartao;
import me.rayll.proposta.cartao.CartaoRepository;
import me.rayll.proposta.cartao.StatusCartao;

@EnableScheduling @Component
public class BloqueioDeCartaoAPILegadaSchedule {
	
	@Value("${api_name}")
	private String nomeApi;
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private BloqueioCartaoAPILegadaFeign bloqueioFeign;
	
	
	@Scheduled(initialDelay = 5000L, fixedDelay = 10000L)
	public void salvarBloqueioAPILegada() {
		
		Set<Cartao> listaCartoes = cartaoRepository.findByStatusCartaoLike(StatusCartao.PENDENTE);
		
		if(listaCartoes.size() > 0) {
			for (Cartao cartao : listaCartoes) {
				
				ResponseEntity<String> request = bloqueioFeign.bloqueioApiLegada(cartao.getId(), Map.of("sistemaResponsavel", nomeApi));
				
				if(request.getStatusCode() == HttpStatus.OK) {
					
					cartao.trocarStatusCartao(StatusCartao.BLOQUEADO);
					cartaoRepository.save(cartao);
				}
			}
		}
	}
	
}
