package model;

import java.io.Serializable;

public class ResponsavelInstituicao implements Serializable {

    private int id;
    private Atleta atleta;

    private String nome;
    private String parentesco;
    private String telefone;
    private String email;

    public ResponsavelInstituicao(
            int id,
            Atleta atleta,
            String nome,
            String parentesco,
            String telefone,
            String email) {

        this.id = id;
        this.atleta = atleta;
        this.nome = nome;
        this.parentesco = parentesco;
        this.telefone = telefone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public Atleta getAtleta() {
        return atleta;
    }

    public String getNome() {
        return nome;
    }

    public String getParentesco() {
        return parentesco;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }
}