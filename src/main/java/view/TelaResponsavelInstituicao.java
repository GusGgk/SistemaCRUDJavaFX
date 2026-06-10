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

import model.Atleta;
import model.ResponsavelInstituicao;

import repository.AtletaRepository;
import repository.ResponsavelInstituicaoRepository;

import util.AlertaUtil;

public class TelaResponsavelInstituicao extends Application {

    private ResponsavelInstituicaoRepository responsavelRepository;
    private AtletaRepository atletaRepository;

    public TelaResponsavelInstituicao(
            ResponsavelInstituicaoRepository responsavelRepository) {

        this.responsavelRepository = responsavelRepository;
        this.atletaRepository = new AtletaRepository();
    }

    public TelaResponsavelInstituicao() {

        this.responsavelRepository =
                new ResponsavelInstituicaoRepository();

        this.atletaRepository =
                new AtletaRepository();
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

        ComboBox<Atleta> campoAtleta =
                new ComboBox<>();

        campoAtleta.getItems().addAll(
                atletaRepository.listarAtletas()
        );

        TextField campoNome = new TextField();
        campoNome.setPromptText("Nome do Responsável");

        TextField campoParentesco = new TextField();
        campoParentesco.setPromptText("Parentesco");

        TextField campoTelefone = new TextField();
        campoTelefone.setPromptText("Telefone");

        TextField campoEmail = new TextField();
        campoEmail.setPromptText("Email");

        Button btnCadastrar =
                new Button("Cadastrar");

        btnCadastrar.setOnAction(evento -> {

            try {

                int id =
                        Integer.parseInt(
                                campoId.getText()
                        );

                Atleta atleta =
                        campoAtleta.getValue();

                String nome =
                        campoNome.getText();

                String parentesco =
                        campoParentesco.getText();

                String telefone =
                        campoTelefone.getText();

                String email =
                        campoEmail.getText();

                if(atleta == null
                        || nome.isEmpty()
                        || parentesco.isEmpty()
                        || telefone.isEmpty()
                        || email.isEmpty()) {

                    throw new Exception(
                            "Preencha todos os campos"
                    );
                }

                ResponsavelInstituicao responsavel =
                        new ResponsavelInstituicao(
                                id,
                                atleta,
                                nome,
                                parentesco,
                                telefone,
                                email
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
                                campoAtleta.getValue(),
                                campoNome.getText(),
                                campoParentesco.getText(),
                                campoTelefone.getText(),
                                campoEmail.getText()
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
                        "Responsável removido!"
                );
            }
        });

        Button btnLimpar =
                new Button("Limpar");

        btnLimpar.setOnAction(evento -> {

            campoId.clear();
            campoNome.clear();
            campoParentesco.clear();
            campoTelefone.clear();
            campoEmail.clear();

            campoAtleta.setValue(null);

            campoId.setEditable(true);
        });

        TableColumn<ResponsavelInstituicao,Integer>
                colunaId =
                new TableColumn<>("ID");

        colunaId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        TableColumn<ResponsavelInstituicao,String>
                colunaAtleta =
                new TableColumn<>("Atleta");

        colunaAtleta.setCellValueFactory(
                cellData ->
                        new javafx.beans.property.SimpleStringProperty(
                                cellData.getValue()
                                        .getAtleta()
                                        .getNome()
                        )
        );

        TableColumn<ResponsavelInstituicao,String>
                colunaNome =
                new TableColumn<>("Responsável");

        colunaNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        TableColumn<ResponsavelInstituicao,String>
                colunaParentesco =
                new TableColumn<>("Parentesco");

        colunaParentesco.setCellValueFactory(
                new PropertyValueFactory<>("parentesco")
        );

        tabela.getColumns().addAll(
                colunaId,
                colunaAtleta,
                colunaNome,
                colunaParentesco
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

                                campoAtleta.setValue(
                                        selecionado.getAtleta()
                                );

                                campoNome.setText(
                                        selecionado.getNome()
                                );

                                campoParentesco.setText(
                                        selecionado.getParentesco()
                                );

                                campoTelefone.setText(
                                        selecionado.getTelefone()
                                );

                                campoEmail.setText(
                                        selecionado.getEmail()
                                );
                            }
                        }
                );

        GridPane formulario = new GridPane();

        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setAlignment(Pos.CENTER);

        formulario.add(new Label("ID:"),0,0);
        formulario.add(campoId,1,0);

        formulario.add(new Label("Atleta:"),0,1);
        formulario.add(campoAtleta,1,1);

        formulario.add(new Label("Nome:"),0,2);
        formulario.add(campoNome,1,2);

        formulario.add(new Label("Parentesco:"),0,3);
        formulario.add(campoParentesco,1,3);

        formulario.add(new Label("Telefone:"),0,4);
        formulario.add(campoTelefone,1,4);

        formulario.add(new Label("Email:"),0,5);
        formulario.add(campoEmail,1,5);

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
                new Scene(raiz, 800, 700);

        stage.setScene(scene);
        stage.show();
    }
}