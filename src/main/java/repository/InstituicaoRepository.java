package repository;

import model.Instituicao;

import java.io.*;
import java.util.ArrayList;

public class InstituicaoRepository {

    private ArrayList<Instituicao> instituicoes = new ArrayList<>();
    private final String caminhoArquivo = "data/instituicoes.dat";

    public InstituicaoRepository() {
        carregarArquivo();
    }

    public boolean adicionarInstituicao(Instituicao instituicao) {
        if (instituicao == null) {
            return false;
        } else if (buscarPorIdInstituicao(instituicao.getId()) != null) {
            return false;
        }

        instituicoes.add(instituicao);
        salvarArquivo();
        return true;
    }

    public ArrayList<Instituicao> listarInstituicoes() {
        return instituicoes;
    }

    public Instituicao buscarPorIdInstituicao(int id) {
        for (Instituicao instituicao : instituicoes) {
            if (instituicao.getId() == id) {
                return instituicao;
            }
        }
        return null;
    }

    public boolean atualizarInstituicao(Instituicao instituicaoAtualizada) {
        if (instituicaoAtualizada == null) {
            return false;
        }

        for (int i = 0; i < instituicoes.size(); i++) {
            if (instituicoes.get(i).getId() == instituicaoAtualizada.getId()) {
                instituicoes.set(i, instituicaoAtualizada);
                salvarArquivo();
                return true;
            }
        }

        return false;
    }

    public boolean deletarInstituicao(int id) {
        boolean removeu = instituicoes.removeIf(
                instituicao -> instituicao.getId() == id
        );

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

            ObjectOutputStream oos =
                    new ObjectOutputStream(
                            new FileOutputStream(caminhoArquivo)
                    );

            oos.writeObject(instituicoes);
            oos.close();

        } catch (IOException e) {
            System.out.println("Erro ao salvar instituições: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarArquivo() {

        File arquivo = new File(caminhoArquivo);

        if (!arquivo.exists()) {
            return;
        }

        try {

            ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(caminhoArquivo)
                    );

            instituicoes =
                    (ArrayList<Instituicao>) ois.readObject();

            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar instituições: " + e.getMessage());
        }
    }
}