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
import model.Atleta;
import model.Candidatura;
import model.Vaga;
import repository.AtletaRepository;
import repository.CandidaturaRepository;
import repository.VagaRepository;
import util.AlertaUtil;

import java.time.LocalDate;

public class TelaCandidatura extends Application {

    private CandidaturaRepository candidaturaRepository;
    private AtletaRepository atletaRepository;
    private VagaRepository vagaRepository;

    public TelaCandidatura(
            CandidaturaRepository candidaturaRepository,
            AtletaRepository atletaRepository,
            VagaRepository vagaRepository) {
        this.candidaturaRepository = candidaturaRepository;
        this.atletaRepository = atletaRepository;
        this.vagaRepository = vagaRepository;
    }

    public TelaCandidatura() {
        this.candidaturaRepository = new CandidaturaRepository();
        this.atletaRepository = new AtletaRepository();
        this.vagaRepository = new VagaRepository();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) throws Exception {
        TableView<Candidatura> tabelaCandidaturas = new TableView<>();

        stage.setTitle("DRAFT - TELA CANDIDATURA");
        Label titulo = new Label("Cadastro de Candidaturas");
        titulo.setFont(new Font("Arial", 24));

        TextField campoId = new TextField();
        campoId.setPromptText("ID da candidatura");

        ComboBox<Atleta> comboAtleta = new ComboBox<>();
        comboAtleta.getItems().addAll(atletaRepository.listarAtletas());
        comboAtleta.setPromptText("Selecione um Atleta");
        configurarComboAtleta(comboAtleta);

        ComboBox<Vaga> comboVaga = new ComboBox<>();
        comboVaga.getItems().addAll(vagaRepository.listarVagas());
        comboVaga.setPromptText("Selecione uma Vaga");
        configurarComboVaga(comboVaga);

        DatePicker campoData = new DatePicker();
        campoData.setPromptText("Data da candidatura");

        ComboBox<String> comboStatus = new ComboBox<>();
        comboStatus.getItems().addAll("Enviada", "Em analise", "Aprovada", "Recusada");
        comboStatus.setPromptText("Status");

        Button btnCadastrar = new Button("Cadastrar Candidatura");
        btnCadastrar.setOnAction(evento -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                Atleta atleta = comboAtleta.getValue();
                Vaga vaga = comboVaga.getValue();
                LocalDate data = campoData.getValue();
                String status = comboStatus.getValue();

                validarCampos(atleta, vaga, data, status);

                Candidatura candidatura = new Candidatura(id, atleta, vaga, data, status);
                boolean sucesso = candidaturaRepository.adicionarCandidatura(candidatura);

                if (sucesso) {
                    AlertaUtil.mostrarSucesso("Candidatura cadastrada com sucesso!");
                    tabelaCandidaturas.getItems().setAll(candidaturaRepository.listarCandidaturas());
                    limparCampos(campoId, comboAtleta, comboVaga, campoData, comboStatus);
                } else {
                    AlertaUtil.mostrarErro("Erro ao cadastrar candidatura (verifique se o ID ja existe).");
                }
            } catch (NumberFormatException e) {
                AlertaUtil.erroNumeroInvalido();
            } catch (Exception e) {
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        Button btnAtualizar = new Button("Atualizar Candidatura");
        btnAtualizar.setOnAction(evento -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                Atleta atleta = comboAtleta.getValue();
                Vaga vaga = comboVaga.getValue();
                LocalDate data = campoData.getValue();
                String status = comboStatus.getValue();

                validarCampos(atleta, vaga, data, status);

                Candidatura selecionada = tabelaCandidaturas.getSelectionModel().getSelectedItem();
                if (selecionada == null) {
                    throw new Exception("Selecione uma candidatura na tabela para atualizar.");
                }

                Candidatura candidatura = new Candidatura(id, atleta, vaga, data, status);
                boolean sucesso = candidaturaRepository.atualizarCandidatura(candidatura);

                if (sucesso) {
                    AlertaUtil.mostrarSucesso("Candidatura atualizada com sucesso!");
                    tabelaCandidaturas.getItems().setAll(candidaturaRepository.listarCandidaturas());
                } else {
                    AlertaUtil.mostrarErro("Erro ao atualizar candidatura.");
                }
            } catch (NumberFormatException e) {
                AlertaUtil.erroNumeroInvalido();
            } catch (Exception e) {
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        Button btnDeletar = new Button("Deletar Candidatura");
        btnDeletar.setOnAction(evento -> {
            Candidatura selecionada = tabelaCandidaturas.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                candidaturaRepository.deletarCandidatura(selecionada.getId());
                AlertaUtil.mostrarSucesso("Candidatura deletada com sucesso!");
                tabelaCandidaturas.getItems().setAll(candidaturaRepository.listarCandidaturas());
                limparCampos(campoId, comboAtleta, comboVaga, campoData, comboStatus);
            } else {
                AlertaUtil.mostrarAviso("Selecione uma candidatura para deletar.");
            }
        });

        Button btnLimpar = new Button("Limpar Campos");
        btnLimpar.setOnAction(evento -> {
            limparCampos(campoId, comboAtleta, comboVaga, campoData, comboStatus);
        });

        TableColumn<Candidatura, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Candidatura, String> colunaAtleta = new TableColumn<>("Atleta");
        colunaAtleta.setCellValueFactory(new PropertyValueFactory<>("nomeAtleta"));

        TableColumn<Candidatura, String> colunaVaga = new TableColumn<>("Vaga");
        colunaVaga.setCellValueFactory(new PropertyValueFactory<>("tituloVaga"));

        TableColumn<Candidatura, LocalDate> colunaData = new TableColumn<>("Data");
        colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));

        TableColumn<Candidatura, String> colunaStatus = new TableColumn<>("Status");
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tabelaCandidaturas.getColumns().addAll(colunaId, colunaAtleta, colunaVaga, colunaData, colunaStatus);
        tabelaCandidaturas.getItems().setAll(candidaturaRepository.listarCandidaturas());

        tabelaCandidaturas.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionada) -> {
            if (selecionada != null) {
                campoId.setEditable(false);
                campoId.setText(String.valueOf(selecionada.getId()));
                selecionarAtleta(comboAtleta, selecionada.getAtleta());
                selecionarVaga(comboVaga, selecionada.getVaga());
                campoData.setValue(selecionada.getData());
                comboStatus.setValue(selecionada.getStatus());
            }
        });

        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setAlignment(Pos.CENTER);

        formulario.add(new Label("ID:"), 0, 0);
        formulario.add(campoId, 1, 0);

        formulario.add(new Label("Atleta:"), 0, 1);
        formulario.add(comboAtleta, 1, 1);

        formulario.add(new Label("Vaga:"), 0, 2);
        formulario.add(comboVaga, 1, 2);

        formulario.add(new Label("Data:"), 0, 3);
        formulario.add(campoData, 1, 3);

        formulario.add(new Label("Status:"), 0, 4);
        formulario.add(comboStatus, 1, 4);

        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER);
        botoes.getChildren().addAll(btnCadastrar, btnAtualizar, btnLimpar, btnDeletar);

        VBox raiz = new VBox(20);
        raiz.setPadding(new Insets(20));
        raiz.setAlignment(Pos.TOP_CENTER);
        raiz.getChildren().addAll(titulo, formulario, botoes, tabelaCandidaturas);

        Scene scene = new Scene(raiz, 700, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void validarCampos(Atleta atleta, Vaga vaga, LocalDate data, String status) throws Exception {
        if (atleta == null || vaga == null || data == null || status == null || status.isEmpty()) {
            throw new Exception("Todos os campos obrigatorios devem ser preenchidos.");
        }
    }

    private void limparCampos(
            TextField campoId,
            ComboBox<Atleta> comboAtleta,
            ComboBox<Vaga> comboVaga,
            DatePicker campoData,
            ComboBox<String> comboStatus) {
        campoId.setEditable(true);
        campoId.clear();
        comboAtleta.setValue(null);
        comboVaga.setValue(null);
        campoData.setValue(null);
        comboStatus.setValue(null);
    }

    private void selecionarAtleta(ComboBox<Atleta> comboAtleta, Atleta atletaSelecionado) {
        if (atletaSelecionado == null) {
            comboAtleta.setValue(null);
            return;
        }

        for (Atleta atleta : comboAtleta.getItems()) {
            if (atleta.getId() == atletaSelecionado.getId()) {
                comboAtleta.setValue(atleta);
                return;
            }
        }
    }

    private void selecionarVaga(ComboBox<Vaga> comboVaga, Vaga vagaSelecionada) {
        if (vagaSelecionada == null) {
            comboVaga.setValue(null);
            return;
        }

        for (Vaga vaga : comboVaga.getItems()) {
            if (vaga.getId() == vagaSelecionada.getId()) {
                comboVaga.setValue(vaga);
                return;
            }
        }
    }

    private void configurarComboAtleta(ComboBox<Atleta> comboAtleta) {
        comboAtleta.setCellFactory(param -> new ListCell<Atleta>() {
            @Override
            protected void updateItem(Atleta item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " (ID: " + item.getId() + ")");
                }
            }
        });

        comboAtleta.setButtonCell(new ListCell<Atleta>() {
            @Override
            protected void updateItem(Atleta item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " (ID: " + item.getId() + ")");
                }
            }
        });
    }

    private void configurarComboVaga(ComboBox<Vaga> comboVaga) {
        comboVaga.setCellFactory(param -> new ListCell<Vaga>() {
            @Override
            protected void updateItem(Vaga item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitulo() + " (ID: " + item.getId() + ")");
                }
            }
        });

        comboVaga.setButtonCell(new ListCell<Vaga>() {
            @Override
            protected void updateItem(Vaga item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitulo() + " (ID: " + item.getId() + ")");
                }
            }
        });
    }
}
