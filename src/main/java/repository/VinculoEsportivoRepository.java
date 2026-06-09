package repository;

import model.VinculoEsportivo;

import java.io.*;
import java.util.ArrayList;

public class VinculoEsportivoRepository {
    private ArrayList<VinculoEsportivo> vinculos = new ArrayList<>();
    private final String caminhoArquivo = "data/vinculos.dat";

    public VinculoEsportivoRepository() {
        carregarArquivo();
    }

    public boolean adicionarVinculo(VinculoEsportivo vinculo) {
        if (vinculo == null) {
            return false;
        } else if (buscarPorIdVinculo(vinculo.getId()) != null) {
            return false;
        }
        vinculos.add(vinculo);
        salvarArquivo();
        return true;
    }

    public ArrayList<VinculoEsportivo> listarVinculos() {
        return vinculos;
    }

    public VinculoEsportivo buscarPorIdVinculo(int id) {
        for (VinculoEsportivo vinculo : vinculos) {
            if (vinculo.getId() == id) {
                return vinculo;
            }
        }
        return null;
    }

    public boolean atualizarVinculo(VinculoEsportivo vinculoAtualizado) {
        if (vinculoAtualizado == null) {
            return false;
        }
        for (int i = 0; i < vinculos.size(); i++) {
            if (vinculos.get(i).getId() == vinculoAtualizado.getId()) {
                vinculos.set(i, vinculoAtualizado);
                salvarArquivo();
                return true;
            }
        }
        return false;
    }

    public boolean deletarVinculo(int id) {
        boolean removeu = vinculos.removeIf(vinculo -> vinculo.getId() == id);
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
            oos.writeObject(vinculos);
            oos.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar vinculos: " + e.getMessage());
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
            vinculos = (ArrayList<VinculoEsportivo>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar vinculos: " + e.getMessage());
        }
    }
}
