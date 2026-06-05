package model;

import java.io.Serializable;

public class RequisitoVaga implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Vaga vaga; // remete ao objeto vaga
    private int idadeMinima;
    private int idadeMaxima;
    private String nivelTecnico; //ex: amador ou profisisonal
    private String disponibilidade;  // ex: integral ou parcial
    private String observacoes;

    //construtor
    public RequisitoVaga(int id, Vaga vaga, int idadeMinima, int idadeMaxima, String nivelTecnico, String disponibilidade, String observacoes) {
        this.id = id;
        this.vaga = vaga;
        this.idadeMinima = idadeMinima;
        this.idadeMaxima = idadeMaxima;
        this.nivelTecnico = nivelTecnico;
        this.disponibilidade = disponibilidade;
        this.observacoes = observacoes;
    }

    // getter e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    // Métodos de conveniência usados pelas colunas do TableView do JavaFX
    public int getIdVaga() {
        return vaga != null ? vaga.getId() : -1;
    }

    public String getTituloVaga() {
        return vaga != null ? vaga.getTitulo() : "Sem Vaga";
    }

    public int getIdadeMinima() {
        return idadeMinima;
    }

    public void setIdadeMinima(int idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public int getIdadeMaxima() {
        return idadeMaxima;
    }

    public void setIdadeMaxima(int idadeMaxima) {
        this.idadeMaxima = idadeMaxima;
    }

    public String getNivelTecnico() {
        return nivelTecnico;
    }

    public void setNivelTecnico(String nivelTecnico) {
        this.nivelTecnico = nivelTecnico;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public void mostrarDados() {
        System.out.println("===== REQUISITOS DA VAGA =====");
        System.out.println("ID Requisito: " + this.id);
        System.out.println("Vaga Vinculada: " + getTituloVaga() + " (ID Vaga: " + getIdVaga() + ")");
        System.out.println("Faixa Etária: " + this.idadeMinima + " a " + this.idadeMaxima + " anos");
        System.out.println("Nível Técnico: " + this.nivelTecnico);
        System.out.println("Disponibilidade: " + this.disponibilidade);
        System.out.println("Observações: " + this.observacoes);
        System.out.println("-----------------------------");
    }
}
