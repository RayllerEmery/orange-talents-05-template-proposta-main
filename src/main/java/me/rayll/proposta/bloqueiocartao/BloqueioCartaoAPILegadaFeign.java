package me.rayll.proposta.bloqueiocartao;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(url = "http://localhost:8888/api/cartoes", value = "bloqueioCartaoFeign")
public interface BloqueioCartaoAPILegadaFeign {
	
	@PostMapping("/{id}/bloqueios")
	public ResponseEntity<String> bloqueioApiLegada(@PathVariable String id, @RequestBody Map<String, Object> aplicacao);
}
