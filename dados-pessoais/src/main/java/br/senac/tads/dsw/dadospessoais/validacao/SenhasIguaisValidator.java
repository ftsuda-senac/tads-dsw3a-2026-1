package br.senac.tads.dsw.dadospessoais.validacao;

import br.senac.tads.dsw.dadospessoais.Pessoa;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenhasIguaisValidator implements
	ConstraintValidator<SenhasIguais, Pessoa> {

	private String mensagem;

	@Override
	public void initialize(SenhasIguais annotation) {
		// Pega a mensagem configurada na anotação @SenhasIguais
		this.mensagem = annotation.message();
	}


	@Override
	public boolean isValid(Pessoa pessoa, ConstraintValidatorContext context) {
		// if (pessoa != null && pessoa.getSenha().equals(pessoa.getSenhaRepeticao())) {
		// 	return true;
		// } else {
		// 	return false;
		// }
	 	boolean resultado = pessoa != null && pessoa.getSenha().equals(pessoa.getSenhaRepeticao());
		// Lógica para associar o erro ao campo de senha
		if (!resultado) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(mensagem)
				.addPropertyNode("senha").addConstraintViolation();
		}
		return resultado;
	}


}
