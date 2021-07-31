package me.rayll.proposta.novaproposta;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import me.rayll.proposta.associacaocarteira.AssociacaoCarteira;
import me.rayll.proposta.cartao.Cartao;

@Entity
public class Proposta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String documento;
    private String email;
    private String nome;
    
    @Embedded
    private EnderecoProposta endereco;
    private BigDecimal salario;
    private EstadoProposta estadoProposta;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proposta")
    private Set<AssociacaoCarteira> carteiras = new HashSet<>();
    
    @OneToOne(cascade = CascadeType.PERSIST)
	private Cartao cartao;

    @Deprecated
    private Proposta(){}

    public Proposta(String documento, String email, String nome, BigDecimal salario, EnderecoProposta endereco, EstadoProposta proposta) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.salario = salario;
        this.endereco = endereco;
        this.estadoProposta = proposta;
        
    }

    public PropostaDTO toDTO() {
        PropostaDTO propostaDTO = new PropostaDTO(
                this.documento,
                this.email,
                this.nome,
                this.salario,
                this.endereco
        );
        propostaDTO.setEstadoProposta(this.estadoProposta);
        propostaDTO.setId(this.id);
        
        if(this.cartao != null)
        	propostaDTO.setCartao(this.cartao.getId());
        
        return propostaDTO;
    }

	public void setEstadoProposta(EstadoProposta estadoProposta2) {
		this.estadoProposta = estadoProposta2;
	}

	public void setCartao(Cartao novoCartao) {
		this.cartao = novoCartao;
	}

	public String getEmail() {
		return this.email;
	}

	public Set<AssociacaoCarteira> getCarteiras() {
		return carteiras;
	}

	public void associarCarteira(AssociacaoCarteira carteira) {
		this.carteiras.add(carteira);
	}
}
