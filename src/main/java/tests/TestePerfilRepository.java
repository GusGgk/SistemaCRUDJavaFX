package tests;

import model.Atleta;
import model.PerfilEsportivo;
import repository.PerfilAtletaRepository;

import java.time.LocalDate;

public class TestePerfilRepository {
    static void main(String[] args) {
        PerfilAtletaRepository perfilAtletaRepository = new PerfilAtletaRepository();
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
                45,
                "dede",
                LocalDate.of(2006, 6, 4),
                "Brasileiro",
                "Curitiba"
        );

        PerfilEsportivo pe1 = new PerfilEsportivo(
                1,
                a1,
                "Futebol",
                "Atacante",
                173,64,
                "Destro","Canhoto",
                "Sou um bom jogador"
        );

        PerfilEsportivo pe2 = new PerfilEsportivo(
                2,
                a2,
                "Basquete",
                "Pivô",
                185,75,
                "Destro","Destro",
                "Sou um bom jogador de basquete"
        );

        System.out.println("Adicionando os perfis");
        System.out.println(perfilAtletaRepository.adicionarPerfil(pe1));
        System.out.println(perfilAtletaRepository.adicionarPerfil(pe2));

        System.out.println("Listando perfis");
        for(PerfilEsportivo perfilEsportivo: perfilAtletaRepository.listarPerfis()){
            perfilEsportivo.mostrarDados();
        }

        System.out.println("Buscando perfil pelo ID do perfil (1)");
        PerfilEsportivo perfilEncontrado = perfilAtletaRepository.buscarPorIdPerfil(1);
        if(perfilEncontrado != null){
            perfilEncontrado.mostrarDados();
        } else{
            System.out.println("Perfil de atleta não encontrado");
        }


        System.out.println("Buscando perfil pelo id do Atleta (45)");
        PerfilEsportivo perfilIdAtleta = perfilAtletaRepository.buscarPerfilPorIdAtleta(45);
        if (perfilIdAtleta != null){
            perfilIdAtleta.mostrarDados();
        } else{
            System.out.println("Perfil de atleta não encontrado.");
        }

        System.out.println("Atualizando perfil do Atleta id 45");
        PerfilEsportivo perfilAtualizado = new PerfilEsportivo(
                2,
                a2,
                "Futsal",
                "Ala",
                185,75,
                "Destro","Destro",
                "Sou um bom jogador de futsal ALTERADO"
        );
        System.out.println(perfilAtletaRepository.atualizarPerfil(perfilAtualizado));

        System.out.println("Deletando o perfil de id 1");
        System.out.println(perfilAtletaRepository.deletarPerfil(1));

        System.out.println("Mostrando lista pela ultima vez no teste:");
        for(PerfilEsportivo perfilEsportivo: perfilAtletaRepository.listarPerfis()){
            perfilEsportivo.mostrarDados();
        }
    }
}
