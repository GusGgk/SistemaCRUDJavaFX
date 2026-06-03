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

import model.Instituicao;
import repository.InstituicaoRepository;
import util.AlertaUtil;

public class TelaInstituicao extends Application {

    private InstituicaoRepository instituicaoRepository;

    public TelaInstituicao(InstituicaoRepository instituicaoRepository){
        this.instituicaoRepository = instituicaoRepository;
    }

    public TelaInstituicao(){
        this.instituicaoRepository = new InstituicaoRepository();
    }

    @Override
    public void start(Stage stage) {

        TableView<Instituicao> tabelaInstituicoes = new TableView<>();

        stage.setTitle("DRAFT - TELA INSTITUIÇÃO");

        Label titulo = new Label("Cadastro de Instituições");
        titulo.setFont(new Font("Arial", 24));

        TextField campoId = new TextField();
        campoId.setPromptText("ID");

        TextField campoNome = new TextField();
        campoNome.setPromptText("Nome");

        TextField campoTipo = new TextField();
        campoTipo.setPromptText("Tipo");

        TextField campoCidade = new TextField();
        campoCidade.setPromptText("Cidade");

        TextField campoContato = new TextField();
        campoContato.setPromptText("Contato");

        Button btnCadastrar = new Button("Cadastrar Instituição");

        btnCadastrar.setOnAction(evento -> {
            try {

                int id = Integer.parseInt(campoId.getText());
                String nome = campoNome.getText();
                String tipo = campoTipo.getText();
                String cidade = campoCidade.getText();
                String contato = campoContato.getText();

                if(nome.isEmpty() || tipo.isEmpty()
                        || cidade.isEmpty() || contato.isEmpty()){
                    throw new Exception("Preencha todos os campos!");
                }

                Instituicao instituicao =
                        new Instituicao(id, nome, tipo, cidade, contato);

                boolean sucesso =
                        instituicaoRepository.adicionarInstituicao(instituicao);

                if(sucesso){
                    AlertaUtil.mostrarSucesso("Instituição cadastrada com sucesso!");
                    tabelaInstituicoes.getItems().setAll(
                            instituicaoRepository.listarInstituicoes()
                    );
                } else {
                    AlertaUtil.mostrarErro("Erro ao cadastrar instituição.");
                }

            } catch (NumberFormatException e){
                AlertaUtil.erroNumeroInvalido();
            } catch (Exception e){
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        Button btnAtualizar = new Button("Atualizar Instituição");

        btnAtualizar.setOnAction(evento -> {
            try {

                int id = Integer.parseInt(campoId.getText());

                String nome = campoNome.getText();
                String tipo = campoTipo.getText();
                String cidade = campoCidade.getText();
                String contato = campoContato.getText();

                if(nome.isEmpty() || tipo.isEmpty()
                        || cidade.isEmpty() || contato.isEmpty()){
                    throw new Exception("Preencha todos os campos!");
                }

                Instituicao instituicao =
                        new Instituicao(id, nome, tipo, cidade, contato);

                boolean sucesso =
                        instituicaoRepository.atualizarInstituicao(instituicao);

                if(sucesso){
                    AlertaUtil.mostrarSucesso("Instituição atualizada!");
                    tabelaInstituicoes.getItems().setAll(
                            instituicaoRepository.listarInstituicoes()
                    );
                } else {
                    AlertaUtil.mostrarErro("Erro ao atualizar instituição.");
                }

            } catch (Exception e){
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        Button btnDeletar = new Button("Deletar Instituição");

        btnDeletar.setOnAction(evento -> {

            Instituicao selecionada =
                    tabelaInstituicoes.getSelectionModel().getSelectedItem();

            if(selecionada != null){

                instituicaoRepository.deletarInstituicao(
                        selecionada.getId()
                );

                tabelaInstituicoes.getItems().setAll(
                        instituicaoRepository.listarInstituicoes()
                );

                AlertaUtil.mostrarSucesso(
                        "Instituição removida com sucesso!"
                );

            } else {
                AlertaUtil.mostrarAviso(
                        "Selecione uma instituição para deletar."
                );
            }
        });

        Button btnLimpar = new Button("Limpar Campos");

        btnLimpar.setOnAction(evento -> {

            campoId.setEditable(true);

            campoId.clear();
            campoNome.clear();
            campoTipo.clear();
            campoCidade.clear();
            campoContato.clear();

            tabelaInstituicoes.getSelectionModel().clearSelection();
        });

        TableColumn<Instituicao, Integer> colunaId =
                new TableColumn<>("ID");
        colunaId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        TableColumn<Instituicao, String> colunaNome =
                new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        TableColumn<Instituicao, String> colunaTipo =
                new TableColumn<>("Tipo");
        colunaTipo.setCellValueFactory(
                new PropertyValueFactory<>("tipo")
        );

        TableColumn<Instituicao, String> colunaCidade =
                new TableColumn<>("Cidade");
        colunaCidade.setCellValueFactory(
                new PropertyValueFactory<>("cidade")
        );

        TableColumn<Instituicao, String> colunaContato =
                new TableColumn<>("Contato");
        colunaContato.setCellValueFactory(
                new PropertyValueFactory<>("contato")
        );

        tabelaInstituicoes.getColumns().addAll(
                colunaId,
                colunaNome,
                colunaTipo,
                colunaCidade,
                colunaContato
        );

        tabelaInstituicoes.getItems().setAll(
                instituicaoRepository.listarInstituicoes()
        );

        tabelaInstituicoes.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, antiga, selecionada) -> {

                            if(selecionada != null){

                                campoId.setEditable(false);

                                campoId.setText(
                                        String.valueOf(selecionada.getId())
                                );

                                campoNome.setText(
                                        selecionada.getNome()
                                );

                                campoTipo.setText(
                                        selecionada.getTipo()
                                );

                                campoCidade.setText(
                                        selecionada.getCidade()
                                );

                                campoContato.setText(
                                        selecionada.getContato()
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

        formulario.add(new Label("Nome:"),0,1);
        formulario.add(campoNome,1,1);

        formulario.add(new Label("Tipo:"),0,2);
        formulario.add(campoTipo,1,2);

        formulario.add(new Label("Cidade:"),0,3);
        formulario.add(campoCidade,1,3);

        formulario.add(new Label("Contato:"),0,4);
        formulario.add(campoContato,1,4);

        HBox botoes = new HBox(10);

        botoes.setAlignment(Pos.CENTER);

        botoes.getChildren().addAll(
                btnCadastrar,
                btnAtualizar,
                btnLimpar,
                btnDeletar
        );

        VBox raiz = new VBox(20);

        raiz.setPadding(new Insets(20));
        raiz.setAlignment(Pos.TOP_CENTER);

        raiz.getChildren().addAll(
                titulo,
                formulario,
                botoes,
                tabelaInstituicoes
        );

        Scene scene = new Scene(raiz,700,700);

        stage.setScene(scene);
        stage.show();
    }
}