/*package tests;

import model.Atleta;
import repository.AtletaRepository;
import java.time.LocalDate;

public class TesteAtletaRepository {
    public static void main(String[] args) {
        AtletaRepository repository = new AtletaRepository();
        Atleta a1 = new Atleta(
                "Gustavo",
                "gukumagai@gmail.com",
                "****",
                1,
                "Gus",
                LocalDate.of(2006, 8, 26),
                "Brasileiro",
                "Curitiba"
        );

        Atleta a2 = new Atleta(
                "André",
                "andre@gmail.com",
                "123",
                2,
                "dede",
                LocalDate.of(2006, 6, 4),
                "Brasileiro",
                "Curitiba"
        );

        System.out.println("Adicionando atletas");
        System.out.println(repository.adicionarAtleta(a1));
        System.out.println(repository.adicionarAtleta(a2));

        System.out.println("Lista de atletas:");
        for(Atleta atleta: repository.listarAtletas()){
            atleta.mostrarDados();
        }

        System.out.println("Buscando atleta id 1");
        Atleta encontrado = repository.buscarPorIdAtleta(1);
        if(encontrado != null){
            encontrado.mostrarDados();
        } else {
            System.out.println("Atleta não encontrado");
        }

        System.out.println("Atualizando atleta 2");
        Atleta atletaAtualizado = new Atleta(
                "Adré att",
                "andre@gmail.com",
                "123",
                2,
                "DeNovo",
                LocalDate.of(2007,7,4),
                "Brasileiro",
                "SP"
        );
        System.out.println(repository.atualizarAtleta(atletaAtualizado));

        System.out.println("Atletas pos atualização:");
        for (Atleta atleta : repository.listarAtletas()){
            atleta.mostrarDados();
        }

        System.out.println("Apagando usuário id 2");
        System.out.println(repository.deletarAtleta(2));

        System.out.println("Lista final:");
        for (Atleta atleta : repository.listarAtletas()){
            atleta.mostrarDados();
        }
    }
   }
*/
