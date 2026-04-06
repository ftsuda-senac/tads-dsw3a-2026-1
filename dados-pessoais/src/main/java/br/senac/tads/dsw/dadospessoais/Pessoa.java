package br.senac.tads.dsw.dadospessoais;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public class Pessoa {

	private Integer id;

	@NotBlank(message = "Preencha o username seu animal") // @NotNull + @NotEmpty
	@Size(min = 2, max = 32)
	private String username;

	@NotBlank
	@Size(min = 1, max = 100)
	private String nome;

	@NotBlank
	@Size(max = 100)
	@Email
	private String email;

	@NotNull
	@PastOrPresent
	private LocalDate dataNascimento;

	public Pessoa() {
	}

	public Pessoa(Integer id, String username, String nome, String email, LocalDate dataNascimento) {
		this.id = id;
		this.username = username;
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
