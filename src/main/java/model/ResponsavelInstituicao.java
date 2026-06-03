package model;

import java.io.Serializable;

public class ResponsavelInstituicao implements Serializable {

    private int id;
    private String nome;
    private String cargo;
    private String email;
    private String telefone;

    public ResponsavelInstituicao(int id, String nome, String cargo, String email, String telefone) {

        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.email = email;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
}