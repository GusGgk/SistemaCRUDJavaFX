package model;

import java.io.Serializable;

public class Instituicao implements Serializable {

    private int id;
    private String nome;
    private String tipo;
    private String cidade;
    private String contato;

    public Instituicao(int id, String nome, String tipo, String cidade, String contato) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.cidade = cidade;
        this.contato = contato;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCidade() {
        return cidade;
    }

    public String getContato() {
        return contato;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}