package br.senac.tads.dsw.dadospessoais;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
}
