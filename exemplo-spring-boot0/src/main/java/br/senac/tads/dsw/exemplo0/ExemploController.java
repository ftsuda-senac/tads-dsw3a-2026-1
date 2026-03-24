package br.senac.tads.dsw.exemplo0;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tools.jackson.databind.json.JsonMapper;

@RestController
public class ExemploController {

    @GetMapping
    public String gerarJson() {
        Exemplo ex = new Exemplo(1, "Ciclano de Souza", 
            "ciclano@email.com");
        String json = """
            {
                "id": %d,
                "nome": "%s",
                "email": "%s"
            }
            """.formatted(ex.getId(), ex.getNome(), ex.getEmail());
            return json;
    }

    @GetMapping("/v2")
    public String gerarJsonV2() {
        Exemplo exemplo = new Exemplo(2, "Fulano da Silva",
            "fulano@email.com");
        // Jackson 3
        JsonMapper jsonMapper = JsonMapper.builder().build();
        return jsonMapper.writeValueAsString(exemplo);
    }

    @GetMapping("/v3")
    public Exemplo gerarJsonV3() {
        return new Exemplo(3, "Beltrana dos Santos", 
            "beltrana@email.com");
    }

}
