package repository;

import model.Atleta;
import model.PerfilEsportivo;

import java.util.ArrayList;

public class PerfilAtletaRepository {
    ArrayList<PerfilEsportivo> perfis = new ArrayList<>();

    public boolean adicionarPerfil(PerfilEsportivo perfilAtleta){
        if(perfilAtleta == null){
            return false;
        } else if (buscarPerfilPorIdAtleta(perfilAtleta.getId()) != null) {
            return false;
        }
        perfis.add(perfilAtleta);
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
                return true;
            }
        }
        return false;
    }

    public boolean deletarPerfil(int id){
        return perfis.removeIf(perfilAtleta -> perfilAtleta.getId() == id);
    }
}
