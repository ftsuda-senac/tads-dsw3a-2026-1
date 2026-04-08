package br.senac.tads.dsw.dadospessoais;

// EXCETO OS AJUSTES ACIMA, NÃO PRECISA MEXER NA LÓGICA

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    private AtomicInteger contador = new AtomicInteger(0);

    private Map<String, PessoaDto> mapPessoas = new ConcurrentHashMap<>(
		Map.ofEntries(
			Map.entry("fulano", new PessoaDto(contador.incrementAndGet(),
				"fulano", "Fulano da Silva",
				"fulano@email.com", LocalDate.parse("2000-10-20"))),
			Map.entry("ciclano", new PessoaDto(contador.incrementAndGet(),
				"ciclano", "Ciclano de Souza",
				"ciclano@email.com", LocalDate.parse("1999-05-10"))),
			Map.entry("beltrana", new PessoaDto(contador.incrementAndGet(),
				"beltrana", "Beltrana dos Santos",
				"beltrana@email.com", LocalDate.parse("2001-02-23")))
    	));

    public List<PessoaDto> obterPessoas() {
        return new ArrayList<>(mapPessoas.values());
    }

    public Optional<PessoaDto> obterPessoa(String username) {
		PessoaDto p = mapPessoas.get(username);
        return Optional.ofNullable(p);
    }

	public PessoaDto incluirNovo(PessoaDto pessoa) {
		pessoa.setId(contador.incrementAndGet());
		mapPessoas.put(pessoa.getUsername(), pessoa);
		return pessoa;
	}

	public PessoaDto alterar(String username, PessoaAlteracaoDto alteracoes) {
		if (!mapPessoas.containsKey(username)) {
			// ERRO
			throw new NaoEncontradoException("Pessoa " + username + " não encontrada");
		}
		PessoaDto pessoaCadastrada = mapPessoas.get(username);
		pessoaCadastrada.setNome(alteracoes.getNome());
		pessoaCadastrada.setEmail(alteracoes.getEmail());
		pessoaCadastrada.setDataNascimento(alteracoes.getDataNascimento());
		pessoaCadastrada.setConhecimentos(alteracoes.getConhecimentos());
		return pessoaCadastrada;
	}

	public void excluir(String username) {
		if (!mapPessoas.containsKey(username)) {
			// ERRO
			throw new NaoEncontradoException("Pessoa " + username + " não encontrada");
		}
		mapPessoas.remove(username);
	}

}
