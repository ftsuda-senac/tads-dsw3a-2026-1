package br.senac.tads.dsw.exemplo0;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SuperExemplo extends Exemplo {

    private LocalDate dataNascimento;

    private String time;

    public SuperExemplo(int id, String nome, String email, 
            LocalDate dataNascimento, String time) {
        super(id, nome, email);
        this.dataNascimento = dataNascimento;
        this.time = time;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getIdade() {
        if (dataNascimento != null) {
            return ChronoUnit.YEARS
                .between(dataNascimento, LocalDate.now());
        }
        return -1;
    }

    public String getTelefone() {
        return "(11) 98765-1234";
    }

}
