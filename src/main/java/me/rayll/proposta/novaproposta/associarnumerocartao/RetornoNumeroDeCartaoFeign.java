package me.rayll.proposta.novaproposta.associarnumerocartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8888/api/cartoes", name = "criarCartao")
public interface RetornoNumeroDeCartaoFeign {
	
	@GetMapping
	public String criarCartaoString(@RequestParam("idProposta") String idProposta);
	
}
