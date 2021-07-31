package me.rayll.proposta.novaproposta;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.bouncycastle.util.encoders.Hex;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import me.rayll.proposta.novaproposta.consultadedados.PropostaAprovacao;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class PropostaDTO {

    @JsonIgnore
    private Long id;
    @CPF @NotEmpty
    private String documento;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String nome;
    @NotNull
    @Positive
    private BigDecimal salario;
    @NotNull
    private EnderecoProposta endereco;

    private EstadoProposta estadoProposta  = EstadoProposta.NAO_ELEGIVEL;
    
    private String cartao = "";

    @Deprecated
    private PropostaDTO() {
    }

    public PropostaDTO(String documento, String email, String nome, BigDecimal salario, EnderecoProposta endereco) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.salario = salario;
        this.endereco = endereco;
        
    }

    public PropostaDTO(Long id, @CPF String documento, @NotEmpty @Email String email, @NotEmpty String nome,
                           @NotNull @Positive BigDecimal salario, @NotNull EnderecoProposta endereco, EstadoProposta estadoProposta) {
        this.id = id;
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.salario = salario;
        this.endereco = endereco;
        this.estadoProposta = estadoProposta;       
    }

    public Proposta toModel() {
        return new Proposta(
                this.documento,
                this.email,
                this.nome,
                this.salario,
                this.endereco,
                this.estadoProposta);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public EnderecoProposta getEndereco() {
        return endereco;
    }

    public EstadoProposta getEstadoProposta() {
        return estadoProposta;
    }

    public void setEstadoProposta(EstadoProposta estadoProposta) {
        this.estadoProposta = estadoProposta;
    }

    public PropostaAprovacao toPropostaAprovacao() {
        return new PropostaAprovacao(documento, nome, id);
    }

	public String getCartao() {
		return cartao;
	}

	public void setCartao(String cartao) {
		this.cartao = cartao;
	}
	
	public void encriptarDocumento(String documento) {

        TextEncryptor textEncryptor = Encryptors.queryableText(
                "pass",
                new String(Hex.encode("salt".getBytes(StandardCharsets.UTF_8)))
        );
        this.documento = textEncryptor.encrypt(documento);
	}
}
