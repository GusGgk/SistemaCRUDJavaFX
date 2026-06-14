package repository;

import model.HistoricoCandidatura;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class HistoricoCandidaturaRepository {
    private ArrayList<HistoricoCandidatura> historicos = new ArrayList<>();
    private final String caminhoArquivo = "data/historicos_candidaturas.dat";

    public HistoricoCandidaturaRepository() {
        carregarArquivo();
    }

    public boolean adicionarHistorico(HistoricoCandidatura historico) {
        if (historico == null || buscarPorIdHistorico(historico.getId()) != null) {
            return false;
        }
        historicos.add(historico);
        salvarArquivo();
        return true;
    }

    public ArrayList<HistoricoCandidatura> listarHistoricos() {
        return historicos;
    }

    public HistoricoCandidatura buscarPorIdHistorico(int id) {
        for (HistoricoCandidatura historico : historicos) {
            if (historico.getId() == id) {
                return historico;
            }
        }
        return null;
    }

    public boolean atualizarHistorico(HistoricoCandidatura historicoAtualizado) {
        if (historicoAtualizado == null) {
            return false;
        }

        for (int i = 0; i < historicos.size(); i++) {
            if (historicos.get(i).getId() == historicoAtualizado.getId()) {
                historicos.set(i, historicoAtualizado);
                salvarArquivo();
                return true;
            }
        }
        return false;
    }

    public boolean deletarHistorico(int id) {
        boolean removeu = historicos.removeIf(historico -> historico.getId() == id);
        if (removeu) {
            salvarArquivo();
        }
        return removeu;
    }

    private void salvarArquivo() {
        try {
            File pasta = new File("data");
            if (!pasta.exists()) {
                pasta.mkdir();
            }

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo));
            oos.writeObject(historicos);
            oos.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar historicos de candidaturas: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarArquivo() {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return;
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo));
            historicos = (ArrayList<HistoricoCandidatura>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar historicos de candidaturas: " + e.getMessage());
        }
    }
}
