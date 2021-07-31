package me.rayll.proposta.avisodeviagem;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(url = "http://localhost:8888/api/cartoes", value = "avisoViagem")
public interface AvisoDeViagemFeign {
	
	@PatchMapping("/{id}")
	public ResponseEntity<String> avisoDeViagemApiLegada(@PathVariable String id, @RequestBody @Valid AvisoDeViagemLegado object);
}
