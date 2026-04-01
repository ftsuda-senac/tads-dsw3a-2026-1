package br.senac.tads.dsw.dadospessoais;

// EXCETO OS AJUSTES ACIMA, NÃO PRECISA MEXER NA LÓGICA

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    private AtomicInteger contador = new AtomicInteger(0);

    private Map<String, Pessoa> mapPessoas = Map.ofEntries(
        Map.entry("fulano", new Pessoa(contador.incrementAndGet(),
            "fulano", "Fulano da Silva",
            "fulano@email.com", LocalDate.parse("2000-10-20"))),
        Map.entry("ciclano", new Pessoa(contador.incrementAndGet(),
            "ciclano", "Ciclano de Souza",
            "ciclano@email.com", LocalDate.parse("1999-05-10"))),
        Map.entry("beltrana", new Pessoa(contador.incrementAndGet(),
            "beltrana", "Beltrana dos Santos",
            "beltrana@email.com", LocalDate.parse("2001-02-23")))
    );

    public List<Pessoa> obterPessoas() {
        return new ArrayList<>(mapPessoas.values());
    }

    public Optional<Pessoa> obterPessoa(String username) {
		Pessoa p = mapPessoas.get(username);
        return Optional.ofNullable(p);
    }
}
