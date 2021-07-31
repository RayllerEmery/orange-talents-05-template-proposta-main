package me.rayll.proposta.cartao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, String>{
	
	public boolean existsById(String id);

	public Set<Cartao> findByStatusCartaoLike(StatusCartao pendente);
}
