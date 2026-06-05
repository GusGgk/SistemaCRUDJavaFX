package repository;

import model.Vaga;
import java.io.*;
import java.util.ArrayList;

public class VagaRepository {
    private ArrayList<Vaga> vagas = new ArrayList<>();

    private final String caminhoArquivo = "data/vagas.dat"; //caminho do arquivo binario com os dados salvos

    //construtor (carrega os dados do arquivo).
    public VagaRepository() {
        carregarArquivo();
    }

    //create
    public boolean adicionarVaga(Vaga vaga) {
        if (vaga == null) {
            return false;
        } else if (buscarPorIdVaga(vaga.getId()) != null) {
            return false;
        }
        vagas.add(vaga);
        salvarArquivo();
        return true;
    }

    //read
    public ArrayList<Vaga> listarVagas() {
        return vagas;
    }

    //read  pro id
    public Vaga buscarPorIdVaga(int id) {
        for (Vaga vaga : vagas) {
            if (vaga.getId() == id) {
                return vaga;
            }
        }
        return null;
    }

    //udate
    public boolean atualizarVaga(Vaga vagaAtualizada) {
        if (vagaAtualizada == null) {
            return false;
        }
        for (int i = 0; i < vagas.size(); i++) {
            if (vagas.get(i).getId() == vagaAtualizada.getId()) {
                vagas.set(i, vagaAtualizada); // Substitui a vaga antiga
                salvarArquivo();
                return true;
            }
        }
        return false;
    }

    //delete
    public boolean deletarVaga(int id) {
        boolean removeu = vagas.removeIf(vaga -> vaga.getId() == id);
        if (removeu) {
            salvarArquivo();
        }
        return removeu;
    }

    //grava a lista vagas no arquivo binário
    private void salvarArquivo() {
        try {
            File pasta = new File("data");
            if (!pasta.exists()) {
                pasta.mkdir(); // Cria a pasta data caso ela não exista
            }
            // Abre o fluxo de saída para gravação de objetos
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo));
            oos.writeObject(vagas);
            oos.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar vagas: " + e.getMessage());
        }
    }

    // Lê a lista do arquivo binário e reconstrói o ArrayList na memória
    @SuppressWarnings("unchecked")
    private void carregarArquivo() {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return; // Se o arquivo não existe (primeira execução), não faz nada
        }
        try {
            // Abre o fluxo de entrada para leitura de objetos
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo));
            vagas = (ArrayList<Vaga>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar vagas: " + e.getMessage());
        }
    }
}
