package br.senac.tads.dsw.dadospessoais;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public List<PessoaDto> obterPessoas() {
		return pessoaService.obterPessoas();
	}

	@GetMapping("/{username}")
	public PessoaDto obterPessoa(@PathVariable("username") String username) {

		Optional<PessoaDto> optPessoa = pessoaService.obterPessoa(username);
		if (optPessoa.isEmpty()) {
			// NAO EXISTE PESSOA COM username INFORMADO
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		PessoaDto pessoa = optPessoa.get();
		return pessoa;
	}

	@PostMapping("/sem-validacao")
	public ResponseEntity<?> incluirNovoSemValidacao(@RequestBody PessoaDto pessoa) {
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
	public ResponseEntity<?> incluirNovo(@RequestBody @Valid PessoaDto pessoa) {
		pessoaService.incluirNovo(pessoa);
		URI location = ServletUriComponentsBuilder //
			.fromCurrentRequestUri() //
			.replacePath("/pessoas/{username}") // // Para remover o /validacao
			.buildAndExpand(pessoa.getUsername()) //
			.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{username}")
	public ResponseEntity<?> alterarPessoa(
		@PathVariable("username") String username,
		@RequestBody @Valid PessoaAlteracaoDto alteracoes) {

		PessoaDto pessoaAlterada = pessoaService.alterar(username, alteracoes);
		return ResponseEntity.ok().body(pessoaAlterada);
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<?> excluirPessoa(@PathVariable("username") String username) {
		pessoaService.excluir(username);
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(NaoEncontradoException.class)
	public ResponseEntity<?> tratarNaoEncontradoException(NaoEncontradoException ex) {
		// ProblemDetails - RFC 7807 e RFC 9457
		ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		return ResponseEntity.of(pd).build();
	}
}
