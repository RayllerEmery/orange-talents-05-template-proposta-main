package me.rayll.proposta.bloqueiocartao;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import me.rayll.proposta.cartao.Cartao;
import me.rayll.proposta.cartao.CartaoRepository;
import me.rayll.proposta.cartao.StatusCartao;

@RestController
@RequestMapping("/bloqueiocartao")
@Transactional
public class BloqueioDeCartaoController {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private BloqueioRepository bloqueioRepository;
	
	@PostMapping("/{idCartao}")
	public ResponseEntity<Void> solicitacaoDeBloqueio(@PathVariable String idCartao, @RequestBody @Valid BloqueioCartaoDTO dto, UriComponentsBuilder uriComponentsBuilder) {
		
		Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não existe!"));
		
	
		if(cartao.getStatusCartao() != StatusCartao.ATIVO) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}
		
		cartao.trocarStatusCartao(StatusCartao.PENDENTE);
		cartaoRepository.save(cartao);
		
		BloqueioCartao bc = bloqueioRepository.save(dto.toModel(cartao));
		
		URI uri = uriComponentsBuilder.path("/bloqueiocartao/{id}")
		.buildAndExpand(idCartao).toUri();
		
		return ResponseEntity.status(HttpStatus.OK).header("Location", uri.toString()).build();
	}
}
