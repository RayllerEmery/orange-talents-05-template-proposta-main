package me.rayll.proposta.metrica;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;

@Component
public class MinhasMetricas {

	private final MeterRegistry meterRegistry;

	public MinhasMetricas(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
	}
	
	public void meuContador() {
	    Collection<Tag> tags = new ArrayList<>();
	    tags.add(Tag.of("emissora", "Mastercard"));
	    tags.add(Tag.of("banco", "Itaú"));

	    Counter contadorDePropostasCriadas = this.meterRegistry.counter("proposta_criada", tags);
	    
	    contadorDePropostasCriadas.increment();
	}
}
