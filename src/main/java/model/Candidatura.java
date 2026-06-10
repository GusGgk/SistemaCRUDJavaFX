package model;
import java.io.Serializable;
import java.time.LocalDate;

public class Candidatura implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Atleta atleta;
    private Vaga vaga;
    private LocalDate data;
    private String status;

    public Candidatura(int id, Atleta atleta, Vaga vaga, LocalDate data, String status) {
        this.id = id;
        this.atleta = atleta;
        this.vaga = vaga;
        this.data = data;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Atleta getAtleta() {
        return atleta;
    }

    public void setAtleta(Atleta atleta) {
        this.atleta = atleta;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomeAtleta() {
        if (atleta == null) {
            return "";
        }
        return atleta.getNome();
    }

    public String getTituloVaga() {
        if (vaga == null) {
            return "";
        }
        return vaga.getTitulo();
    }
}
