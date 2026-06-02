package repository;
import model.Atleta;

import java.util.ArrayList;

public class AtletaRepository {
private ArrayList<Atleta> atletas = new ArrayList<>();

    public boolean adicionarAtleta(Atleta atleta){
        if (atleta == null){
            return false;
        } else if (buscarPorIdAtleta(atleta.getId()) !=null) {
            return false;
        }
        atletas.add(atleta);
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
                return true;
            }
        }
        return false;
    }

    public boolean deletarAtleta(int id){
        return atletas.removeIf(atleta -> atleta.getId() == id);
    }
}
