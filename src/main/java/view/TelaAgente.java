package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Agente;
import repository.AgenteRepository;
import util.AlertaUtil;

public class TelaAgente extends Application {

    private AgenteRepository agenteRepository;

    public TelaAgente(AgenteRepository agenteRepository) {
        this.agenteRepository = agenteRepository;
    }

    public TelaAgente() {
        this.agenteRepository = new AgenteRepository();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) throws Exception {
        TableView<Agente> tabelaAgentes = new TableView<>();

        stage.setTitle("DRAFT - TELA AGENTE");
        Label titulo = new Label("Cadastro de Agentes");
        titulo.setFont(new Font("Arial", 24));

        TextField campoId = new TextField();
        campoId.setPromptText("ID");

        TextField campoNome = new TextField();
        campoNome.setPromptText("Nome");

        TextField campoEmail = new TextField();
        campoEmail.setPromptText("Email");

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Senha");

        TextField campoCref = new TextField();
        campoCref.setPromptText("Registro CREF");

        TextField campoTelefone = new TextField();
        campoTelefone.setPromptText("Telefone");

        Button btnCadastrar = new Button("Cadastrar Agente");
        btnCadastrar.setOnAction(evento -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                String nome = campoNome.getText();
                String email = campoEmail.getText();
                String senha = campoSenha.getText();
                String cref = campoCref.getText();
                String telefone = campoTelefone.getText();

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cref.isEmpty() || telefone.isEmpty()) {
                    throw new Exception("Todos os campos obrigatórios devem ser preenchidos.");
                }

                Agente agente = new Agente(nome, email, senha, id, cref, telefone);
                boolean sucesso = agenteRepository.adicionarAgente(agente);

                if (sucesso) {
                    AlertaUtil.mostrarSucesso("Agente cadastrado com sucesso!");
                    tabelaAgentes.getItems().setAll(agenteRepository.listarAgentes());
                } else {
                    AlertaUtil.mostrarErro("Erro ao cadastrar agente (verifique se o ID já existe).");
                }
            } catch (NumberFormatException e) {
                AlertaUtil.mostrarErro("O campo ID deve ser preenchido com um número.");
            } catch (Exception e) {
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        Button btnAtualizar = new Button("Atualizar Agente");
        btnAtualizar.setOnAction(evento -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                String nome = campoNome.getText();
                String email = campoEmail.getText();
                String senha = campoSenha.getText();
                String cref = campoCref.getText();
                String telefone = campoTelefone.getText();

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cref.isEmpty() || telefone.isEmpty()) {
                    throw new Exception("Todos os campos obrigatórios devem ser preenchidos.");
                }

                Agente selecionado = tabelaAgentes.getSelectionModel().getSelectedItem();
                if (selecionado == null) {
                    throw new Exception("Selecione um agente na tabela para atualizar.");
                }

                Agente agente = new Agente(nome, email, senha, id, cref, telefone);
                boolean sucesso = agenteRepository.atualizarAgente(agente);

                if (sucesso) {
                    AlertaUtil.mostrarSucesso("Agente atualizado com sucesso!");
                    tabelaAgentes.getItems().setAll(agenteRepository.listarAgentes());
                } else {
                    AlertaUtil.mostrarErro("Erro ao atualizar agente.");
                }
            } catch (NumberFormatException e) {
                AlertaUtil.mostrarErro("O campo ID deve ser preenchido com um número.");
            } catch (Exception e) {
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        Button btnDeletar = new Button("Deletar Agente");
        btnDeletar.setOnAction(evento -> {
            Agente selecionado = tabelaAgentes.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                agenteRepository.deletarAgente(selecionado.getId());
                AlertaUtil.mostrarSucesso("Agente deletado com sucesso!");
                tabelaAgentes.getItems().setAll(agenteRepository.listarAgentes());
            } else {
                AlertaUtil.mostrarAviso("Selecione um agente para deletar.");
            }
        });

        Button btnLimpar = new Button("Limpar Campos");
        btnLimpar.setOnAction(evento -> {
            campoId.setEditable(true);
            campoId.clear();
            campoNome.clear();
            campoEmail.clear();
            campoSenha.clear();
            campoCref.clear();
            campoTelefone.clear();
        });

        TableColumn<Agente, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Agente, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Agente, String> colunaCref = new TableColumn<>("CREF");
        colunaCref.setCellValueFactory(new PropertyValueFactory<>("registroCref"));

        TableColumn<Agente, String> colunaTelefone = new TableColumn<>("Telefone");
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        tabelaAgentes.getColumns().addAll(colunaId, colunaNome, colunaCref, colunaTelefone);
        tabelaAgentes.getItems().setAll(agenteRepository.listarAgentes());

        tabelaAgentes.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                campoId.setEditable(false);
                campoId.setText(String.valueOf(selecionado.getId()));
                campoNome.setText(selecionado.getNome());
                campoEmail.setText(selecionado.getEmail());
                campoSenha.setText(selecionado.getSenha());
                campoCref.setText(selecionado.getRegistroCref());
                campoTelefone.setText(selecionado.getTelefone());
            }
        });

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

        formulario.add(new Label("Registro CREF:"), 0, 4);
        formulario.add(campoCref, 1, 4);

        formulario.add(new Label("Telefone:"), 0, 5);
        formulario.add(campoTelefone, 1, 5);

        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER);
        botoes.getChildren().addAll(btnCadastrar, btnAtualizar, btnLimpar, btnDeletar);

        VBox raiz = new VBox(20);
        raiz.setPadding(new Insets(20));
        raiz.setAlignment(Pos.TOP_CENTER);
        raiz.getChildren().addAll(titulo, formulario, botoes, tabelaAgentes);

        Scene scene = new Scene(raiz, 600, 600);
        stage.setScene(scene);
        stage.show();
    }
}
