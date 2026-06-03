package repository;

import model.ResponsavelInstituicao;

import java.io.*;
import java.util.ArrayList;

public class ResponsavelInstituicaoRepository {

    private ArrayList<ResponsavelInstituicao> responsaveis = new ArrayList<>();
    private final String caminhoArquivo = "data/responsaveis.dat";

    public ResponsavelInstituicaoRepository() {
        carregarArquivo();
    }

    public boolean adicionarResponsavel(ResponsavelInstituicao responsavel) {

        if (responsavel == null) {
            return false;
        } else if (buscarPorIdResponsavel(responsavel.getId()) != null) {
            return false;
        }

        responsaveis.add(responsavel);
        salvarArquivo();
        return true;
    }

    public ArrayList<ResponsavelInstituicao> listarResponsaveis() {
        return responsaveis;
    }

    public ResponsavelInstituicao buscarPorIdResponsavel(int id) {

        for (ResponsavelInstituicao responsavel : responsaveis) {

            if (responsavel.getId() == id) {
                return responsavel;
            }

        }

        return null;
    }

    public boolean atualizarResponsavel(ResponsavelInstituicao responsavelAtualizado) {

        if (responsavelAtualizado == null) {
            return false;
        }

        for (int i = 0; i < responsaveis.size(); i++) {

            if (responsaveis.get(i).getId() == responsavelAtualizado.getId()) {

                responsaveis.set(i, responsavelAtualizado);
                salvarArquivo();
                return true;
            }

        }

        return false;
    }

    public boolean deletarResponsavel(int id) {

        boolean removeu =
                responsaveis.removeIf(
                        responsavel -> responsavel.getId() == id
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

            oos.writeObject(responsaveis);
            oos.close();

        } catch (IOException e) {

            System.out.println(
                    "Erro ao salvar responsáveis: "
                            + e.getMessage()
            );

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

            responsaveis =
                    (ArrayList<ResponsavelInstituicao>) ois.readObject();

            ois.close();

        } catch (IOException | ClassNotFoundException e) {

            System.out.println(
                    "Erro ao carregar responsáveis: "
                            + e.getMessage()
            );

        }
    }
}