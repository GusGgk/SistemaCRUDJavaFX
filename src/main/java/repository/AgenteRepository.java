package repository;

import model.Agente;

import java.io.*;
import java.util.ArrayList;

public class AgenteRepository {
    private ArrayList<Agente> agentes = new ArrayList<>();
    private final String caminhoArquivo = "data/agentes.dat";

    public AgenteRepository() {
        carregarArquivo();
    }

    public boolean adicionarAgente(Agente agente) {
        if (agente == null) {
            return false;
        } else if (buscarPorIdAgente(agente.getId()) != null) {
            return false;
        }
        agentes.add(agente);
        salvarArquivo();
        return true;
    }

    public ArrayList<Agente> listarAgentes() {
        return agentes;
    }

    public Agente buscarPorIdAgente(int id) {
        for (Agente agente : agentes) {
            if (agente.getId() == id) {
                return agente;
            }
        }
        return null;
    }

    public boolean atualizarAgente(Agente agenteAtualizado) {
        if (agenteAtualizado == null) {
            return false;
        }
        for (int i = 0; i < agentes.size(); i++) {
            if (agentes.get(i).getId() == agenteAtualizado.getId()) {
                agentes.set(i, agenteAtualizado);
                salvarArquivo();
                return true;
            }
        }
        return false;
    }

    public boolean deletarAgente(int id) {
        boolean removeu = agentes.removeIf(agente -> agente.getId() == id);
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
            oos.writeObject(agentes);
            oos.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar agentes: " + e.getMessage());
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
            agentes = (ArrayList<Agente>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar agentes: " + e.getMessage());
        }
    }
}
