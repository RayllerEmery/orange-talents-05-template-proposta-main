package me.rayll.proposta.associacaocarteira;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.rayll.proposta.cartao.Cartao;
import me.rayll.proposta.cartao.CartaoRepository;
import me.rayll.proposta.novaproposta.Proposta;
import me.rayll.proposta.novaproposta.PropostaRepository;

@RestController
@RequestMapping("/associacaocarteira")
public class AssociacaoCarteiraController {

	@Autowired
	private PropostaRepository propostaRepository;
	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private AssociacaoCarteiraFeign feign;
	@Autowired
	private AssociacaoCarteiraRepository associacaoRepository;

	@PostMapping("/{id}")
	public ResponseEntity<Void> associarCartao(@PathVariable String id, @RequestBody @Valid AssociarCarteriaDTO dto,
			UriComponentsBuilder builder) throws JsonMappingException, JsonProcessingException {
		// busca o cartão pelo numero passado pelo @PathVariable
		Cartao c = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		//busca a proposta pelo cartao para associarmos a carteira
		Proposta proposta = propostaRepository.findByCartao(c)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		//verifica se a proposta buscada é a mesma associada a carteira
		if (proposta.equals(c.getProposta())) {
			//email para salvar na api externa e na nossa api
			String email = proposta.getEmail();
			dto.setEmail(email);

			//salvar na api externa a carteira
			ResponseEntity<String> request = feign.apiExternaAvisoCarteira(id, dto);
			
			//buscas o que precisamos da resposta da api externa
			ObjectMapper mapper = new ObjectMapper();
			String idAssociacao = mapper.readValue(request.getBody(), JsonNode.class).get("id").asText();
			String resultado = mapper.readValue(request.getBody(), JsonNode.class).get("resultado").asText();
			
			//setar valores no dto
			dto.setId(idAssociacao);
			dto.setResultado(resultado);
			
			AssociacaoCarteira model = dto.ToModel();

			proposta.associarCarteira(model);

			propostaRepository.save(proposta);

			String url = builder.path("/associacaocarteira/{id}").buildAndExpand(idAssociacao).toUriString();

			return ResponseEntity.status(HttpStatus.CREATED).header("Location", url).build();

		}

		return ResponseEntity.badRequest().build();

	}
}
