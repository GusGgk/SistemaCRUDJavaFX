package model;

import java.io.Serializable;
import java.time.LocalDate;

public class VinculoEsportivo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Atleta atleta;
    private Instituicao instituicao;
    private LocalDate dataInicio;
    private String status;

    public VinculoEsportivo(int id, Atleta atleta, Instituicao instituicao, LocalDate dataInicio, String status) {
        this.id = id;
        this.atleta = atleta;
        this.instituicao = instituicao;
        this.dataInicio = dataInicio;
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

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getNomeAtleta() {
        return atleta != null ? atleta.getNome() : "";
    }

    public String getNomeInstituicao() {
        return instituicao != null ? instituicao.getNome() : "";
    }
}
