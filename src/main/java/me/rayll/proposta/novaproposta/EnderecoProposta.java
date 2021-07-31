package me.rayll.proposta.novaproposta;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Embeddable
public class EnderecoProposta {

    @NotEmpty
    private String rua;
    @NotNull
    private Integer numeroCasa;
    @NotEmpty
    private String cidade;

    @Deprecated
    private EnderecoProposta() {}

    public EnderecoProposta(String rua, Integer numeroCasa, String cidade) {
        this.rua = rua;
        this.numeroCasa = numeroCasa;
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public Integer getNumeroCasa() {
        return numeroCasa;
    }

    public String getCidade() {
        return cidade;
    }
}
