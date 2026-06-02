package repository;
import model.Atleta;

import java.io.*;
import java.util.ArrayList;

public class AtletaRepository {
private ArrayList<Atleta> atletas = new ArrayList<>();
    private final String caminhoArquivo = "data/atletas.dat";

    public AtletaRepository(){
        carregarArquivo();
    }


    public boolean adicionarAtleta(Atleta atleta){
        if (atleta == null){
            return false;
        } else if (buscarPorIdAtleta(atleta.getId()) !=null) {
            return false;
        }
        atletas.add(atleta);
        salvarArquivo();
        return true;
    }

    public ArrayList<Atleta> listarAtletas(){
        return atletas;
    }

    public Atleta buscarPorIdAtleta(int id){
        for(Atleta atleta : atletas){
            if (atleta.getId() == id){
                return atleta;
            }
        }
        return null;
    }

    public boolean atualizarAtleta(Atleta atletaAtualizado){
        if (atletaAtualizado == null){
            return false;
        }
        for(int i = 0; i < atletas.size(); i++){
            if (atletas.get(i).getId() == atletaAtualizado.getId()){
                atletas.set(i, atletaAtualizado);
                salvarArquivo();
                return true;
            }
        }
        return false;
    }

    public boolean deletarAtleta(int id){
        boolean removeu = atletas.removeIf(atleta -> atleta.getId() == id);
        if(removeu){
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
            oos.writeObject(atletas);
            oos.close();
        } catch (IOException e){
            System.out.println("Erro ao salvar atletas: " + e.getMessage());
        }
    }

    private void carregarArquivo(){
        File arquivo = new File(caminhoArquivo);

        if(!arquivo.exists()){
            return;
        }
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo));
            atletas = (ArrayList<Atleta>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Erro ao carregar atletas: " + e.getMessage());
        }
    }
}
