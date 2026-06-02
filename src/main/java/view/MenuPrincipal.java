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
import repository.PerfilAtletaRepository;

public class MenuPrincipal extends Application {

    @Override
    public void start(Stage stage) {

        AtletaRepository atletaRepository = new AtletaRepository();
        PerfilAtletaRepository perfilRepository = new PerfilAtletaRepository();

        Label titulo = new Label("DRAFT - Menu Principal");
        titulo.setFont(new Font("Arial", 24));

        Button btnAtletas = new Button("Gerenciar Atletas");
        Button btnPerfis = new Button("Gerenciar Perfis Esportivos");

        btnAtletas.setOnAction(evento -> {
            TelaAtleta telaAtleta = new TelaAtleta(atletaRepository);
            try {
                telaAtleta.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnPerfis.setOnAction(evento -> {
            TelaPerfilAtleta telaPerfil = new TelaPerfilAtleta(atletaRepository, perfilRepository);
            try {
                telaPerfil.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        VBox raiz = new VBox(20);
        raiz.setPadding(new Insets(30));
        raiz.setAlignment(Pos.CENTER);
        raiz.getChildren().addAll(titulo, btnAtletas, btnPerfis);

        Scene scene = new Scene(raiz, 400, 300);

        stage.setTitle("DRAFT");
        stage.setScene(scene);
        stage.show();
    }
}