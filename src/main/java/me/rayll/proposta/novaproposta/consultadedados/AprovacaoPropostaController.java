package me.rayll.proposta.novaproposta.consultadedados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import me.rayll.proposta.novaproposta.Proposta;
import me.rayll.proposta.novaproposta.PropostaDTO;
import me.rayll.proposta.novaproposta.PropostaRepository;

@RestController
@RequestMapping("/aprovacaoproposta")
public class AprovacaoPropostaController {
	
	@Autowired
	AprovacaoPropostaFeign aprovacaoPropostaFeign;
	
	@Autowired
	PropostaRepository propostaRepository;
	
	@PostMapping("/{id}")
	public ResponseEntity<Void> getAprovacao(@PathVariable Long id){
		//Busca uma proposta por id através do repository
		Proposta proposta = 
				propostaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ""));
		
		PropostaDTO propostaDTO = proposta.toDTO();
		
		//mapeia uma proposta para uma PropostaAprovacao, que irá consultar a api externa
		PropostaAprovacao propostaAprovacao = new PropostaAprovacao(
				propostaDTO.getDocumento(), 
				propostaDTO.getNome(), 
				propostaDTO.getId());
		
		//mapeia outro objeto de PropostaAprovacao com o retorno do endpoint externo
		PropostaAprovacao aprovacao = aprovacaoPropostaFeign.getAprovacao(propostaAprovacao);
		
		//maperia a proposta que buscou com o repository para um dto
		proposta.setEstadoProposta(aprovacao.estadoProposta());
		
		//salvando a proposta com repository
		propostaRepository.save(proposta);
		
		return ResponseEntity.ok().build();
	}
}
