package repository;

import model.Atleta;
import model.PerfilEsportivo;

import java.io.*;
import java.util.ArrayList;

public class PerfilAtletaRepository {
    ArrayList<PerfilEsportivo> perfis = new ArrayList<>();
    private final String caminhoArquivo = "data/perfis.dat";


    public PerfilAtletaRepository(){
        carregarArquivo();
    }

    public boolean adicionarPerfil(PerfilEsportivo perfilAtleta){
        if(perfilAtleta == null){
            return false;
        }

        if (buscarPerfilPorIdAtleta(perfilAtleta.getId()) != null) {
            return false;
        }
        if(buscarPerfilPorIdAtleta(perfilAtleta.getIdAtleta()) != null){
            return false;
        }
        perfis.add(perfilAtleta);
        salvarArquivo();
        return true;
    }
    public ArrayList<PerfilEsportivo> listarPerfis(){
        return perfis;
    }

    public PerfilEsportivo buscarPorIdPerfil(int id){
        for(PerfilEsportivo perfilAtleta : perfis){
            if (perfilAtleta.getId() == id){
                return perfilAtleta;
            }
        }
        return null;
    }
    public PerfilEsportivo buscarPerfilPorIdAtleta(int idAtleta){
        for(PerfilEsportivo perfilAtleta : perfis){
            if (perfilAtleta.getIdAtleta() == idAtleta){
                return perfilAtleta;
            }
        }
        return null;
    }
    public boolean atualizarPerfil(PerfilEsportivo perfilAtualizado){
        if(perfilAtualizado == null){
            return false;
        }
        for(int i = 0; i < perfis.size(); i++){
            if(perfis.get(i).getId() == perfilAtualizado.getId()){
                perfis.set(i, perfilAtualizado);
                salvarArquivo();
                return true;
            }
        }
        return false;
    }

    public boolean deletarPerfil(int id){
        return perfis.removeIf(perfilAtleta -> perfilAtleta.getId() == id);
    }

    private void salvarArquivo(){
        try {
            File pasta = new File("data");
            if(!pasta.exists()){
                pasta.mkdir();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo));
            oos.writeObject(perfis);
            oos.close();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Erro ao salvar perfis: " + e.getMessage());
        }
    }
    private void carregarArquivo() {
        File arquivo = new File(caminhoArquivo);

        if (!arquivo.exists()) {
            return;
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo));
            perfis = (ArrayList<PerfilEsportivo>) ois.readObject();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar perfis: " + e.getMessage());
        }
    }

}

