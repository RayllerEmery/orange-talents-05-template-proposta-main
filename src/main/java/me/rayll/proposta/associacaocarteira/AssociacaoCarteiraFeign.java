package me.rayll.proposta.associacaocarteira;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:8888/api/cartoes", value = "associacaoCarteiraFeign")
public interface AssociacaoCarteiraFeign {
	
	@PostMapping("/{id}/carteiras")
	public ResponseEntity<String> apiExternaAvisoCarteira(@PathVariable String id, @RequestBody @Valid AssociarCarteriaDTO dto);
}
