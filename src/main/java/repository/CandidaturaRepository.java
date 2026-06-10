package repository;
import model.Candidatura;

import java.io.*;
import java.util.ArrayList;

public class CandidaturaRepository {
    private ArrayList<Candidatura> candidaturas = new ArrayList<>();
    private final String caminhoArquivo = "data/candidaturas.dat";

    public CandidaturaRepository() { carregarArquivo();}

    public boolean adicionarCandidatura(Candidatura candidatura){
        if (candidatura == null){
            return false;
        } else if (buscarPorIdCandidatura(candidatura.getId()) !=null) {
            return false;
        }
        candidaturas.add(candidatura);
        salvarArquivo();
        return true;
    }

    public ArrayList<Candidatura> listarCandidaturas() { return candidaturas; }

    public Candidatura buscarPorIdCandidatura(int id){
        for (Candidatura candidatura : candidaturas){
            if (candidatura.getId() == id){
                return candidatura;
            }
        }
        return null;
    }

    public boolean atualizarCandidatura(Candidatura candidaturaAtualizada){
        if (candidaturaAtualizada == null){
            return false;
        }
        for (int i = 0; i < candidaturas.size(); i++){
            if (candidaturas.get(i).getId() == candidaturaAtualizada.getId()){
                candidaturas.set(i, candidaturaAtualizada);
                salvarArquivo();
                return true;
            }
        }
        return false;
    }

    public boolean deletarCandidatura(int id){
        boolean removeu = candidaturas.removeIf(candidatura -> candidatura.getId() == id);
        if (removeu){
            salvarArquivo();
        }
        return removeu;
    }

    private void salvarArquivo(){
        try{
            File pasta = new File("data");
            if (!pasta.exists()){
                pasta.mkdir();
            }

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo));
            oos.writeObject(candidaturas);
            oos.close();
        } catch (IOException e){
            System.out.println("Erro ao salvar candidaturas: " + e.getMessage());
        }
    }

    private void carregarArquivo(){
        File arquivo = new File(caminhoArquivo);

        if(!arquivo.exists()){
            return;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo));
            candidaturas = (ArrayList<Candidatura>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Erro ao carregar candidaturas: " + e.getMessage());
        }

    }


}
