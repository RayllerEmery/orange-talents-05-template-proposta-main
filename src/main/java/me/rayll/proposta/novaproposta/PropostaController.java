package me.rayll.proposta.novaproposta;

import javax.transaction.Transactional;
import javax.validation.Valid;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;


@RestController
@RequestMapping("/novaproposta")
public class PropostaController {

    private final Tracer tracer;
    @Autowired
    private PropostaRepository propostaRepository;


    public PropostaController(Tracer tracer){
        this.tracer = tracer;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> criarNovaProposta(
            @RequestBody @Valid PropostaDTO dto,
            UriComponentsBuilder uriComponentsBuilder){
    	
    	String documentoDTO = dto.getDocumento();
    	dto.encriptarDocumento(documentoDTO);

        //Validação caso já exista uma proposta para o documento
        if(propostaRepository.existsByDocumento(dto.getDocumento())){
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Já existe uma proposta para este registro.");
        }

        //Código para transformar um dto para model e salvar
        Proposta novaProposta = propostaRepository.save(dto.toModel());

        //Proposta criada tranformarda em DTO para poder ser manipulada.
        PropostaDTO propostaSalvaDTO = novaProposta.toDTO();
        
        //Retorno de uma ReponseEntity com o header da localização do recurso e NovaPropostaCriada
        return ResponseEntity.
                    created(uriComponentsBuilder
                            .path("/novaproposta/{id}").buildAndExpand(propostaSalvaDTO.getId()).toUri())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> propostaCriada(@PathVariable Long id, Principal principal){

        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("user.email", principal.getName());

        //Busca a proposta por id, caso não retorna exceção
        Proposta novaProposta = propostaRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proposta não localizada.")
        );
        //mapeia entidade para dto
        PropostaDTO propostaDTO = novaProposta.toDTO();

        return ResponseEntity.ok(propostaDTO);
    }
    
    @PostMapping("/retornaodto")
    public ResponseEntity<PropostaDTO> verDTO(@RequestBody @Valid PropostaDTO dto){


    	return ResponseEntity.ok(dto);
    }
}
