package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import model.Atleta;
import repository.AtletaRepository;


import java.time.LocalDate;

import static javafx.application.Application.launch;

public class TelaAtleta extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AtletaRepository atletaRepository = new AtletaRepository();
        TableView<Atleta> tabelaAtletas = new TableView<>();

        stage.setTitle("DRAFT - TELA ATLETA");
        Label titulo = new Label("Cadastro de Atletas");
        titulo.setFont(new Font("Arial", 24));

        TextField campoId = new TextField();
        campoId.setPromptText("ID");

        TextField campoNome = new TextField();
        campoNome.setPromptText("Nome");

        TextField campoEmail = new TextField();
        campoEmail.setPromptText("Email");

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Senha");

        TextField campoNickname = new TextField();
        campoNickname.setPromptText("Nickname");

        DatePicker campoDataNascimento = new DatePicker();
        campoDataNascimento.setPromptText("Data de nascimento");

        TextField campoNacionalidade = new TextField();
        campoNacionalidade.setPromptText("Nacionalidade");

        TextField campoEndereco = new TextField();
        campoEndereco.setPromptText("Endereço");

        Button btnCadastrar = new Button("Cadastrar Atleta");
        btnCadastrar.setOnAction(evento -> {
            try {
                String nome = campoNome.getText();
                String email = campoEmail.getText();
                String nacionalidade = campoNacionalidade.getText();
                int id = Integer.parseInt(campoId.getText());
                String nickname = campoNickname.getText();
                String endereco = campoEndereco.getText();
                String senha = campoSenha.getText();
                LocalDate dataNascimento = campoDataNascimento.getValue();

                if (nome.isEmpty()||email.isEmpty()||senha.isEmpty()||nacionalidade.isEmpty()||nickname.isEmpty()||endereco.isEmpty() || dataNascimento == null){
                    throw new Exception("Campos obrigatórios vazios");
                }

                Atleta atleta = new Atleta(nome, email, senha, id, nickname, dataNascimento, nacionalidade, endereco);
                boolean sucesso = atletaRepository.adicionarAtleta(atleta);

                if (sucesso) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Sucesso!");
                    alert.setHeaderText(null);
                    alert.setContentText("Atleta cadastrado com sucesso!");
                    alert.showAndWait();
                    tabelaAtletas.getItems().setAll(atletaRepository.listarAtletas());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro!");
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao cadastrar atleta! Tente novamente");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro!");
                alert.setHeaderText(null);
                alert.setContentText("O ID deve ser um número");
                alert.showAndWait();
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro!");
                alert.setHeaderText(null);
                alert.setContentText("Todos os campos devem ser preenchidos");
                alert.showAndWait();
            }


        });




        Button btnAtualizar = new Button("Atualizar Atleta");
        btnAtualizar.setOnAction(evento ->{
            try {
                String nome = campoNome.getText();
                String email = campoEmail.getText();
                String nacionalidade = campoNacionalidade.getText();
                int id = Integer.parseInt(campoId.getText());
                String nickname = campoNickname.getText();
                String endereco = campoEndereco.getText();
                String senha = campoSenha.getText();
                LocalDate dataNascimento = campoDataNascimento.getValue();

                if (nome.isEmpty()||email.isEmpty()||senha.isEmpty()||nacionalidade.isEmpty()||nickname.isEmpty()||endereco.isEmpty() || dataNascimento == null){
                    throw new Exception("Campos obrigatórios vazios");
                }

                Atleta atleta = new Atleta(nome, email, senha, id, nickname, dataNascimento, nacionalidade, endereco);

                Atleta selecionado = tabelaAtletas.getSelectionModel().getSelectedItem();
                if(selecionado == null){
                    throw new Exception("Selecione um atleta para editar");
                }

                boolean sucesso = atletaRepository.atualizarAtleta(atleta);

                if (sucesso) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Sucesso!");
                    alert.setHeaderText(null);
                    alert.setContentText("Atleta editado com sucesso!");
                    alert.showAndWait();
                    tabelaAtletas.getItems().setAll(atletaRepository.listarAtletas());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro!");
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao editar atleta! Tente novamente");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro!");
                alert.setHeaderText(null);
                alert.setContentText("O ID deve ser um número");
                alert.showAndWait();
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro!");
                alert.setHeaderText(null);
                alert.setContentText("Precisa selecionar alguma linha da tabela!");
                alert.showAndWait();
            }

        });



        Button btnDeletar = new Button("Deletar Atleta");
        btnDeletar.setOnAction(evento ->{
            Atleta atletaSelecionado = tabelaAtletas.getSelectionModel().getSelectedItem();

            if (atletaSelecionado != null){
                atletaRepository.deletarAtleta(atletaSelecionado.getId());
                tabelaAtletas.getItems().setAll(atletaRepository.listarAtletas());
            }
        });

        Button btnLimpar = new Button("Limpar Campos");
        btnLimpar.setOnAction(evento -> {
            campoId.setEditable(true);
            campoNome.clear();
            campoEmail.clear();
            campoEndereco.clear();
            campoSenha.clear();
            campoNickname.clear();
            campoNacionalidade.clear();
            campoDataNascimento.setValue(null);
        });



        TableColumn<Atleta, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Atleta, String> colunaNome = new TableColumn<>("Atleta");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Atleta, String> colunaEmail = new TableColumn<>("Email");
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Atleta, LocalDate> colunaDataNascimento = new TableColumn<>("Data de nascimento");
        colunaDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));

        tabelaAtletas.getColumns().addAll(colunaId,colunaNome,colunaEmail,colunaDataNascimento);
        tabelaAtletas.getItems().setAll(atletaRepository.listarAtletas());

        tabelaAtletas.getSelectionModel().selectedItemProperty().addListener(
                (obs, atletaAntigo, atletaSelecionado) -> {
                    if (atletaSelecionado != null) {
                        campoId.setText(String.valueOf(atletaSelecionado.getId()));
                        campoNome.setText(atletaSelecionado.getNome());
                        campoEmail.setText(atletaSelecionado.getEmail());
                        campoSenha.setText(atletaSelecionado.getSenha());
                        campoNickname.setText(atletaSelecionado.getNickname());
                        campoDataNascimento.setValue(atletaSelecionado.getDataNascimento());
                        campoNacionalidade.setText(atletaSelecionado.getNacionalidade());
                        campoEndereco.setText(atletaSelecionado.getEndereco());
                    }
                }
        );

        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setAlignment(Pos.CENTER);

        formulario.add(new Label("ID:"), 0, 0);
        formulario.add(campoId, 1, 0);

        formulario.add(new Label("Nome:"), 0, 1);
        formulario.add(campoNome, 1, 1);

        formulario.add(new Label("Email:"), 0, 2);
        formulario.add(campoEmail, 1, 2);

        formulario.add(new Label("Senha:"), 0, 3);
        formulario.add(campoSenha, 1, 3);

        formulario.add(new Label("Nickname:"), 0, 4);
        formulario.add(campoNickname, 1, 4);

        formulario.add(new Label("Data Nascimento:"), 0, 5);
        formulario.add(campoDataNascimento, 1, 5);

        formulario.add(new Label("Nacionalidade:"), 0, 6);
        formulario.add(campoNacionalidade, 1, 6);

        formulario.add(new Label("Endereço:"), 0, 7);
        formulario.add(campoEndereco, 1, 7);

        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER);
        botoes.getChildren().addAll(btnCadastrar,btnAtualizar,btnLimpar,btnDeletar);

        VBox raiz = new VBox(20);
        raiz.setPadding(new Insets(20));
        raiz.setAlignment(Pos.TOP_CENTER);
        raiz.getChildren().addAll(titulo,formulario,botoes, tabelaAtletas);




        Scene scene = new Scene(raiz, 700, 700);
        stage.setScene(scene);
        stage.show();

    }



    public static void main(String[] args){
        launch();
    }
}
