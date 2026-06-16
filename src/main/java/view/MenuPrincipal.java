package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import repository.AtletaRepository;
import repository.CandidaturaRepository;
import repository.PerfilAtletaRepository;
import repository.InstituicaoRepository;
import repository.ResponsavelInstituicaoRepository;
import repository.VagaRepository;
import repository.RequisitoVagaRepository;
import repository.AgenteRepository;
import repository.VinculoEsportivoRepository;
import repository.HistoricoCandidaturaRepository;

public class MenuPrincipal extends Application {

    @Override
    public void start(Stage stage) {

        AtletaRepository atletaRepository = new AtletaRepository();

        PerfilAtletaRepository perfilRepository = new PerfilAtletaRepository();

        InstituicaoRepository instituicaoRepository = new InstituicaoRepository();

        ResponsavelInstituicaoRepository responsavelRepository = new ResponsavelInstituicaoRepository();

        VagaRepository vagaRepository = new VagaRepository();

        RequisitoVagaRepository requisitoRepository = new RequisitoVagaRepository();
        
        AgenteRepository agenteRepository = new AgenteRepository();

        VinculoEsportivoRepository vinculoEsportivoRepository = new VinculoEsportivoRepository();

        CandidaturaRepository candidaturaRepository = new CandidaturaRepository();

        HistoricoCandidaturaRepository historicoCandidaturaRepository =
                new HistoricoCandidaturaRepository();

        Label titulo = new Label("DRAFT - Menu Principal");
        titulo.setFont(new Font("Arial", 24));

        Button btnAtletas =
                new Button("Gerenciar Atletas");

        Button btnPerfis =
                new Button("Gerenciar Perfis Esportivos");

        Button btnInstituicoes =
                new Button("Gerenciar Instituições");

        Button btnResponsaveis =
                new Button("Gerenciar Responsáveis");

        Button btnVagas =
                new Button("Gerenciar Vagas");

        Button btnRequisitos =
                new Button("Gerenciar Requisitos");

        Button btnAgentes =
                new Button("Gerenciar Agentes");

        Button btnVinculos =
                new Button("Gerenciar Vínculos Esportivos");

        Button btnCandidaturas =
                new Button("Gerenciar Candidaturas");

        Button btnHistoricosCandidaturas =
                new Button("Gerenciar Historicos de Candidaturas");

        btnAtletas.setOnAction(evento -> {
            TelaAtleta telaAtleta =
                    new TelaAtleta(atletaRepository);

            try {
                telaAtleta.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnPerfis.setOnAction(evento -> {
            TelaPerfilAtleta telaPerfil =
                    new TelaPerfilAtleta(
                            atletaRepository,
                            perfilRepository
                    );

            try {
                telaPerfil.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnInstituicoes.setOnAction(evento -> {
            TelaInstituicao telaInstituicao =
                    new TelaInstituicao(
                            instituicaoRepository
                    );

            try {
                telaInstituicao.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnResponsaveis.setOnAction(evento -> {
            TelaResponsavelInstituicao telaResponsavel =
                    new TelaResponsavelInstituicao(
                            responsavelRepository
                    );

            try {
                telaResponsavel.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnVagas.setOnAction(evento -> {
            TelaVaga telaVaga =
                    new TelaVaga(vagaRepository, instituicaoRepository);

            try {
                telaVaga.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnRequisitos.setOnAction(evento -> {
            TelaRequisitoVaga telaReq =
                    new TelaRequisitoVaga(
                            vagaRepository,
                            requisitoRepository
                    );

            try {
                telaReq.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnAgentes.setOnAction(evento -> {
            TelaAgente telaAgente =
                    new TelaAgente(agenteRepository);

            try {
                telaAgente.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnVinculos.setOnAction(evento -> {
            TelaVinculoEsportivo telaVinculo =
                    new TelaVinculoEsportivo(
                            atletaRepository,
                            instituicaoRepository,
                            vinculoEsportivoRepository
                    );

            try {
                telaVinculo.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnCandidaturas.setOnAction(evento -> {
            TelaCandidatura telaCandidatura =
                    new TelaCandidatura(
                            candidaturaRepository,
                            atletaRepository,
                            vagaRepository
                    );

            try {
                telaCandidatura.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnHistoricosCandidaturas.setOnAction(evento -> {
            TelaHistoricoCandidatura telaHistorico =
                    new TelaHistoricoCandidatura(
                            historicoCandidaturaRepository,
                            candidaturaRepository
                    );

            try {
                telaHistorico.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        VBox raiz = new VBox(15);

        raiz.setPadding(new Insets(30));
        raiz.setAlignment(Pos.CENTER);

        raiz.getChildren().addAll(
                titulo,
                btnAtletas,
                btnPerfis,
                btnInstituicoes,
                btnResponsaveis,
                btnVagas,
                btnRequisitos,
                btnAgentes,
                btnVinculos,
                btnCandidaturas,
                btnHistoricosCandidaturas
        );

        Scene scene = new Scene(raiz, 500, 600);

        stage.setTitle("DRAFT");
        stage.setScene(scene);
        stage.show();
    }
}
