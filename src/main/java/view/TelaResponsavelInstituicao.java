package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import model.ResponsavelInstituicao;
import repository.ResponsavelInstituicaoRepository;
import util.AlertaUtil;

public class TelaResponsavelInstituicao extends Application {

    private ResponsavelInstituicaoRepository responsavelRepository;

    public TelaResponsavelInstituicao(
            ResponsavelInstituicaoRepository responsavelRepository) {

        this.responsavelRepository = responsavelRepository;
    }

    public TelaResponsavelInstituicao() {
        this.responsavelRepository =
                new ResponsavelInstituicaoRepository();
    }

    @Override
    public void start(Stage stage) {

        TableView<ResponsavelInstituicao> tabela =
                new TableView<>();

        stage.setTitle("DRAFT - RESPONSÁVEIS");

        Label titulo =
                new Label("Cadastro de Responsáveis");

        titulo.setFont(new Font("Arial", 24));

        TextField campoId = new TextField();
        campoId.setPromptText("ID");

        TextField campoNome = new TextField();
        campoNome.setPromptText("Nome");

        TextField campoCargo = new TextField();
        campoCargo.setPromptText("Cargo");

        TextField campoEmail = new TextField();
        campoEmail.setPromptText("Email");

        TextField campoTelefone = new TextField();
        campoTelefone.setPromptText("Telefone");

        Button btnCadastrar =
                new Button("Cadastrar");

        btnCadastrar.setOnAction(evento -> {

            try {

                int id =
                        Integer.parseInt(
                                campoId.getText()
                        );

                String nome =
                        campoNome.getText();

                String cargo =
                        campoCargo.getText();

                String email =
                        campoEmail.getText();

                String telefone =
                        campoTelefone.getText();

                if(nome.isEmpty()
                        || cargo.isEmpty()
                        || email.isEmpty()
                        || telefone.isEmpty()) {

                    throw new Exception(
                            "Preencha todos os campos"
                    );
                }

                ResponsavelInstituicao responsavel =
                        new ResponsavelInstituicao(
                                id,
                                nome,
                                cargo,
                                email,
                                telefone
                        );

                boolean sucesso =
                        responsavelRepository
                                .adicionarResponsavel(
                                        responsavel
                                );

                if(sucesso) {

                    AlertaUtil.mostrarSucesso(
                            "Responsável cadastrado!"
                    );

                    tabela.getItems().setAll(
                            responsavelRepository
                                    .listarResponsaveis()
                    );

                } else {

                    AlertaUtil.mostrarErro(
                            "Erro ao cadastrar"
                    );
                }

            } catch(NumberFormatException e) {

                AlertaUtil.erroNumeroInvalido();

            } catch(Exception e) {

                AlertaUtil.mostrarErro(
                        e.getMessage()
                );
            }
        });

        Button btnAtualizar =
                new Button("Atualizar");

        btnAtualizar.setOnAction(evento -> {

            try {

                int id =
                        Integer.parseInt(
                                campoId.getText()
                        );

                ResponsavelInstituicao responsavel =
                        new ResponsavelInstituicao(
                                id,
                                campoNome.getText(),
                                campoCargo.getText(),
                                campoEmail.getText(),
                                campoTelefone.getText()
                        );

                boolean sucesso =
                        responsavelRepository
                                .atualizarResponsavel(
                                        responsavel
                                );

                if(sucesso) {

                    AlertaUtil.mostrarSucesso(
                            "Atualizado com sucesso!"
                    );

                    tabela.getItems().setAll(
                            responsavelRepository
                                    .listarResponsaveis()
                    );
                }

            } catch(Exception e) {

                AlertaUtil.mostrarErro(
                        e.getMessage()
                );
            }
        });

        Button btnExcluir =
                new Button("Excluir");

        btnExcluir.setOnAction(evento -> {

            ResponsavelInstituicao selecionado =
                    tabela.getSelectionModel()
                            .getSelectedItem();

            if(selecionado != null) {

                responsavelRepository
                        .deletarResponsavel(
                                selecionado.getId()
                        );

                tabela.getItems().setAll(
                        responsavelRepository
                                .listarResponsaveis()
                );

                AlertaUtil.mostrarSucesso(
                        "Removido com sucesso!"
                );
            }
        });

        Button btnLimpar =
                new Button("Limpar");

        btnLimpar.setOnAction(evento -> {

            campoId.setEditable(true);

            campoId.clear();
            campoNome.clear();
            campoCargo.clear();
            campoEmail.clear();
            campoTelefone.clear();

            tabela.getSelectionModel()
                    .clearSelection();
        });

        TableColumn<ResponsavelInstituicao,Integer>
                colunaId =
                new TableColumn<>("ID");

        colunaId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        TableColumn<ResponsavelInstituicao,String>
                colunaNome =
                new TableColumn<>("Nome");

        colunaNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        TableColumn<ResponsavelInstituicao,String>
                colunaCargo =
                new TableColumn<>("Cargo");

        colunaCargo.setCellValueFactory(
                new PropertyValueFactory<>("cargo")
        );

        TableColumn<ResponsavelInstituicao,String>
                colunaEmail =
                new TableColumn<>("Email");

        colunaEmail.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );

        TableColumn<ResponsavelInstituicao,String>
                colunaTelefone =
                new TableColumn<>("Telefone");

        colunaTelefone.setCellValueFactory(
                new PropertyValueFactory<>("telefone")
        );

        tabela.getColumns().addAll(
                colunaId,
                colunaNome,
                colunaCargo,
                colunaEmail,
                colunaTelefone
        );

        tabela.getItems().setAll(
                responsavelRepository
                        .listarResponsaveis()
        );

        tabela.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, antigo, selecionado) -> {

                            if(selecionado != null) {

                                campoId.setEditable(false);

                                campoId.setText(
                                        String.valueOf(
                                                selecionado.getId()
                                        )
                                );

                                campoNome.setText(
                                        selecionado.getNome()
                                );

                                campoCargo.setText(
                                        selecionado.getCargo()
                                );

                                campoEmail.setText(
                                        selecionado.getEmail()
                                );

                                campoTelefone.setText(
                                        selecionado.getTelefone()
                                );
                            }
                        }
                );

        GridPane formulario =
                new GridPane();

        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setAlignment(Pos.CENTER);

        formulario.add(new Label("ID:"),0,0);
        formulario.add(campoId,1,0);

        formulario.add(new Label("Nome:"),0,1);
        formulario.add(campoNome,1,1);

        formulario.add(new Label("Cargo:"),0,2);
        formulario.add(campoCargo,1,2);

        formulario.add(new Label("Email:"),0,3);
        formulario.add(campoEmail,1,3);

        formulario.add(new Label("Telefone:"),0,4);
        formulario.add(campoTelefone,1,4);

        HBox botoes = new HBox(10);

        botoes.setAlignment(Pos.CENTER);

        botoes.getChildren().addAll(
                btnCadastrar,
                btnAtualizar,
                btnLimpar,
                btnExcluir
        );

        VBox raiz = new VBox(20);

        raiz.setPadding(new Insets(20));
        raiz.setAlignment(Pos.TOP_CENTER);

        raiz.getChildren().addAll(
                titulo,
                formulario,
                botoes,
                tabela
        );

        Scene scene =
                new Scene(raiz,700,700);

        stage.setScene(scene);
        stage.show();
    }
}