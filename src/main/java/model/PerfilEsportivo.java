package model;
import java.io.Serializable;

public class PerfilEsportivo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Atleta atleta;

    private String esporte;
    private String posicao;

    private double altura;
    private double peso;

    private String maoDominante;
    private String peDominante;

    private String bio;

    public PerfilEsportivo(
            int id,
            Atleta atleta,
            String esporte,
            String posicao,
            double altura,
            double peso,
            String maoDominante,
            String peDominante,
            String bio) {

        this.id = id;
        this.atleta = atleta;
        this.esporte = esporte;
        this.posicao = posicao;
        this.altura = altura;
        this.peso = peso;
        this.maoDominante = maoDominante;
        this.peDominante = peDominante;
        this.bio = bio;
    }

    public int getId() {
        return id;
    }

    public String getNomeAtleta(){
        return atleta.getNome();
    }

    public int getIdAtleta() {
        return atleta.getId();
    }

    public Atleta getAtleta() {
        return atleta;
    }

    public void setAtleta(Atleta atleta) {
        this.atleta = atleta;
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

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getMaoDominante() {
        return maoDominante;
    }

    public void setMaoDominante(String maoDominante) {
        this.maoDominante = maoDominante;
    }

    public String getPeDominante() {
        return peDominante;
    }

    public void setPeDominante(String peDominante) {
        this.peDominante = peDominante;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void mostrarDados() {
        System.out.println("===== PERFIL ESPORTIVO =====");
        System.out.println("ID Perfil: " + id);
        System.out.println("ID Atleta: " + getIdAtleta());
        System.out.println("Esporte: " + esporte);
        System.out.println("Posição: " + posicao);
        System.out.println("Altura: " + altura);
        System.out.println("Peso: " + peso);
        System.out.println("Mão dominante: " + maoDominante);
        System.out.println("Pé dominante: " + peDominante);
        System.out.println("Bio: " + bio);
    }
}