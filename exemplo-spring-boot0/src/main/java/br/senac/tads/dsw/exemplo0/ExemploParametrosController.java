package br.senac.tads.dsw.exemplo0;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parametros")
public class ExemploParametrosController {

    @GetMapping("/ex01")
    public Exemplo exemplo01(@RequestParam("nome") String nome,
                             @RequestParam("email") String email) {
        return new Exemplo(1, nome, email);
    }

    @GetMapping("/ex01b")
    public Exemplo exemplo01b(@RequestParam String nome,
                              @RequestParam String email) {
        return new Exemplo(2, nome, email);
    }

    @GetMapping("/ex02")
    public Exemplo exemplo02(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam(name = "dataNascimento", required = false) LocalDate dataNascimento,
            @RequestParam(name = "time", defaultValue = "Sem time") String time) {
        return new SuperExemplo(2, nome, email, dataNascimento, time);
    }

    @GetMapping("/ex03/{username}")
    public String exemplo03(@PathVariable("username") String username) {
        return "{ \"username\": \"%s\" }".formatted(username);
    }

    @GetMapping("/ex04")
    public String exemplo04(@RequestHeader("user-agent") String userAgent) {
        return "{ \"userAgent\": \"%s\" }".formatted(userAgent);
    }

}
