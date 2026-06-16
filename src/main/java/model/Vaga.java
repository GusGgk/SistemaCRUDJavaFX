package model;

import java.io.Serializable; // serve para que o Java permita a escrita do objeto em arquivo binário

public class Vaga implements Serializable {
    private static final long serialVersionUID = 1L; // Controla a compatibilidade de versão na desserialização do arquivo binário

    private int id;
    private String titulo;
    private String esporte;
    private String posicao;
    private Instituicao instituicao;
    private String status; 

    // Construtor
    public Vaga(int id, String titulo, String esporte, String posicao, Instituicao instituicao, String status) {
        this.id = id;
        this.titulo = titulo;
        this.esporte = esporte;
        this.posicao = posicao;
        this.instituicao = instituicao;
        this.status = status;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEsporte() {
        return esporte;
    }

    public void setEsporte(String esporte) {
        this.esporte = esporte;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public String getNomeInstituicao() {
        return instituicao != null ? instituicao.getNome() : "";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void mostrarDados() {
        System.out.println("===== VAGA =====");
        System.out.println("ID: " + this.id);
        System.out.println("Título: " + this.titulo);
        System.out.println("Esporte: " + this.esporte);
        System.out.println("Posição: " + this.posicao);
        System.out.println("Instituição: " + (this.instituicao != null ? this.instituicao.getNome() : "Nenhuma"));
        System.out.println("Status: " + this.status);
        System.out.println("----------------");
    }
}
