package repository;

import model.RequisitoVaga;
import java.io.*;
import java.util.ArrayList;

public class RequisitoVagaRepository {
    private ArrayList<RequisitoVaga> requisitos = new ArrayList<>();
    private final String caminhoArquivo = "data/requisitos.dat";

    public RequisitoVagaRepository() {
        carregarArquivo();
    }

    public boolean adicionarRequisito(RequisitoVaga requisito) {
        if (requisito == null) {
            return false;
        }
        //evita id duplicado
        if (buscarPorIdRequisito(requisito.getId()) != null) {
            return false;
        }
        //evita que a mesma vaga tenha mais de um requisito
        if (buscarRequisitoPorIdVaga(requisito.getIdVaga()) != null) {
            return false;
        }
        requisitos.add(requisito);
        salvarArquivo();
        return true;
    }

    public ArrayList<RequisitoVaga> listarRequisitos() {
        return requisitos;
    }

    public RequisitoVaga buscarPorIdRequisito(int id) {
        for (RequisitoVaga req : requisitos) {
            if (req.getId() == id) {
                return req;
            }
        }
        return null;
    }

    //busca o requisito que está associado a vaga
    public RequisitoVaga buscarRequisitoPorIdVaga(int idVaga) {
        for (RequisitoVaga req : requisitos) {
            if (req.getIdVaga() == idVaga) {
                return req;
            }
        }
        return null;
    }

    public boolean atualizarRequisito(RequisitoVaga requisitoAtualizado) {
        if (requisitoAtualizado == null) {
            return false;
        }
        for (int i = 0; i < requisitos.size(); i++) {
            if (requisitos.get(i).getId() == requisitoAtualizado.getId()) {
                requisitos.set(i, requisitoAtualizado);
                salvarArquivo();
                return true;
            }
        }
        return false;
    }

    public boolean deletarRequisito(int id) {
        boolean removeu = requisitos.removeIf(req -> req.getId() == id);
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
            oos.writeObject(requisitos);
            oos.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar requisitos: " + e.getMessage());
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
            requisitos = (ArrayList<RequisitoVaga>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar requisitos: " + e.getMessage());
        }
    }
}
