package model;

import java.io.Serializable;
import java.time.LocalDate;

public class HistoricoCandidatura implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Candidatura candidatura;
    private LocalDate dataAlteracao;
    private String statusAnterior;
    private String novoStatus;
    private String observacao;

    public HistoricoCandidatura(int id, Candidatura candidatura, LocalDate dataAlteracao, String statusAnterior,
                                String novoStatus, String observacao) {
        this.id = id;
        this.candidatura = candidatura;
        this.dataAlteracao = dataAlteracao;
        this.statusAnterior = statusAnterior;
        this.novoStatus = novoStatus;
        this.observacao = observacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Candidatura getCandidatura() {
        return candidatura;
    }

    public void setCandidatura(Candidatura candidatura) {
        this.candidatura = candidatura;
    }

    public int getIdCandidatura() {
        return candidatura != null ? candidatura.getId() : -1;
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getStatusAnterior() {
        return statusAnterior;
    }

    public void setStatusAnterior(String statusAnterior) {
        this.statusAnterior = statusAnterior;
    }

    public String getNovoStatus() {
        return novoStatus;
    }

    public void setNovoStatus(String novoStatus) {
        this.novoStatus = novoStatus;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
