package br.senac.tads.dsw.dadospessoais;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	private final PessoaService pessoaService;

	// POR QUE DEVE SER ASSIM?
	public PessoaController(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}

	@GetMapping
	public List<Pessoa> obterPessoas() {
		return pessoaService.obterPessoas();
	}

	@GetMapping("/{username}")
	public Pessoa obterPessoa(@PathVariable("username") String username) {

		Optional<Pessoa> optPessoa = pessoaService.obterPessoa(username);
		if (optPessoa.isEmpty()) {
			// NAO EXISTE PESSOA COM username INFORMADO
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		Pessoa pessoa = optPessoa.get();
		return pessoa;
	}

	@PostMapping("/sem-validacao")
	public ResponseEntity<?> incluirNovoSemValidacao(@RequestBody Pessoa pessoa) {
		pessoaService.incluirNovo(pessoa);
		// URI location = URI.create("http://localhost:8080/pessoas/" + pessoa.getUsername());
		URI location = ServletUriComponentsBuilder //
			.fromCurrentRequestUri() //
			.path("/{username}") //
			.buildAndExpand(pessoa.getUsername()) //
			.toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping
	public ResponseEntity<?> incluirNovo(@RequestBody @Valid Pessoa pessoa) {
		pessoaService.incluirNovo(pessoa);
		URI location = ServletUriComponentsBuilder //
			.fromCurrentRequestUri() //
			.replacePath("/pessoas/{username}") // // Para remover o /validacao
			.buildAndExpand(pessoa.getUsername()) //
			.toUri();
		return ResponseEntity.created(location).build();
	}
}
