package br.senac.tads.dsw;

public class Main {
    public static void main(String[] args) {
        Exemplo ex = new Exemplo(1, "Fulano da Silva", "fulano@email.com");
        String json = """
            {
                "id": %d,
                "nome": "%s",
                "email": "%s"
            }    
            """.formatted(ex.getId(), ex.getNome(), ex.getEmail());
        String json2 = String.format("""
            {
                "id": %d,
                "nome": "%s",
                "email": "%s"
            }    
            """, ex.getId(), ex.getNome(), ex.getEmail());
        System.out.println(json);
    }
}