package me.rayll.proposta.cadastrobiometria;

import java.net.URI;
import java.util.Base64;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import me.rayll.proposta.cartao.Cartao;
import me.rayll.proposta.cartao.CartaoRepository;

@RestController
@RequestMapping("/cadastrobiometria")
@Transactional
public class BiometriaController {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private BiometricaRepository biometriaRepository;
	
	@PostMapping
	public ResponseEntity<Void> cadastroBiometrica(@RequestBody @Valid BiometriaDTO dto, UriComponentsBuilder uri){
		
		Cartao c = cartaoRepository.findById(dto.getNumeroCartao()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão inválido!"));
		
		String bioEncoder = Base64.getEncoder().encodeToString(dto.getIdBiometria().getBytes());
		dto.setIdBiometria(bioEncoder);
		
		Biometria biometria = biometriaRepository.save(dto.toModel(c));
		
		URI uri2 = uri.path("/cadastrobiometria/{id}").buildAndExpand( biometria.toDTO().getId()).toUri();
		
		return ResponseEntity.status(HttpStatus.CREATED).header("location", uri2.toString()).build();
	}
}
