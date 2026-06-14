package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Candidatura;
import model.HistoricoCandidatura;
import repository.CandidaturaRepository;
import repository.HistoricoCandidaturaRepository;
import util.AlertaUtil;

import java.time.LocalDate;

public class TelaHistoricoCandidatura extends Application {
    private HistoricoCandidaturaRepository historicoRepository;
    private CandidaturaRepository candidaturaRepository;

    public TelaHistoricoCandidatura(HistoricoCandidaturaRepository historicoRepository,
                                    CandidaturaRepository candidaturaRepository) {
        this.historicoRepository = historicoRepository;
        this.candidaturaRepository = candidaturaRepository;
    }

    public TelaHistoricoCandidatura() {
        this.historicoRepository = new HistoricoCandidaturaRepository();
        this.candidaturaRepository = new CandidaturaRepository();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) throws Exception {
        TableView<HistoricoCandidatura> tabelaHistoricos = new TableView<>();

        stage.setTitle("DRAFT - HISTORICO DE CANDIDATURA");
        Label titulo = new Label("Historico de Candidaturas");
        titulo.setFont(new Font("Arial", 24));

        TextField campoId = new TextField();
        campoId.setPromptText("ID do historico");

        ComboBox<Candidatura> comboCandidatura = new ComboBox<>();
        comboCandidatura.getItems().addAll(candidaturaRepository.listarCandidaturas());
        comboCandidatura.setPromptText("Selecione uma candidatura");
        configurarComboCandidatura(comboCandidatura);

        DatePicker campoDataAlteracao = new DatePicker();
        campoDataAlteracao.setPromptText("Data da alteracao");

        ComboBox<String> comboStatusAnterior = criarComboStatus("Status anterior");
        ComboBox<String> comboNovoStatus = criarComboStatus("Novo status");

        TextField campoObservacao = new TextField();
        campoObservacao.setPromptText("Observacao");

        Button btnCadastrar = new Button("Cadastrar Historico");
        btnCadastrar.setOnAction(evento -> {
            try {
                HistoricoCandidatura historico = criarHistorico(
                        campoId,
                        comboCandidatura,
                        campoDataAlteracao,
                        comboStatusAnterior,
                        comboNovoStatus,
                        campoObservacao);

                if (historicoRepository.adicionarHistorico(historico)) {
                    AlertaUtil.mostrarSucesso("Historico cadastrado com sucesso!");
                    tabelaHistoricos.getItems().setAll(historicoRepository.listarHistoricos());
                    limparCampos(campoId, comboCandidatura, campoDataAlteracao, comboStatusAnterior,
                            comboNovoStatus, campoObservacao);
                } else {
                    AlertaUtil.mostrarErro("Erro ao cadastrar historico (verifique se o ID ja existe).");
                }
            } catch (NumberFormatException e) {
                AlertaUtil.erroNumeroInvalido();
            } catch (Exception e) {
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        Button btnAtualizar = new Button("Atualizar Historico");
        btnAtualizar.setOnAction(evento -> {
            try {
                HistoricoCandidatura selecionado = tabelaHistoricos.getSelectionModel().getSelectedItem();
                if (selecionado == null) {
                    throw new Exception("Selecione um historico na tabela para atualizar.");
                }

                HistoricoCandidatura atualizado = criarHistorico(
                        campoId,
                        comboCandidatura,
                        campoDataAlteracao,
                        comboStatusAnterior,
                        comboNovoStatus,
                        campoObservacao);

                if (historicoRepository.atualizarHistorico(atualizado)) {
                    AlertaUtil.mostrarSucesso("Historico atualizado com sucesso!");
                    tabelaHistoricos.getItems().setAll(historicoRepository.listarHistoricos());
                    limparCampos(campoId, comboCandidatura, campoDataAlteracao, comboStatusAnterior,
                            comboNovoStatus, campoObservacao);
                } else {
                    AlertaUtil.mostrarErro("Erro ao atualizar historico.");
                }
            } catch (NumberFormatException e) {
                AlertaUtil.erroNumeroInvalido();
            } catch (Exception e) {
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        Button btnDeletar = new Button("Deletar Historico");
        btnDeletar.setOnAction(evento -> {
            HistoricoCandidatura selecionado = tabelaHistoricos.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                AlertaUtil.mostrarAviso("Selecione um historico para deletar.");
                return;
            }

            historicoRepository.deletarHistorico(selecionado.getId());
            AlertaUtil.mostrarSucesso("Historico deletado com sucesso!");
            tabelaHistoricos.getItems().setAll(historicoRepository.listarHistoricos());
            limparCampos(campoId, comboCandidatura, campoDataAlteracao, comboStatusAnterior,
                    comboNovoStatus, campoObservacao);
        });

        Button btnLimpar = new Button("Limpar Campos");
        btnLimpar.setOnAction(evento -> limparCampos(
                campoId,
                comboCandidatura,
                campoDataAlteracao,
                comboStatusAnterior,
                comboNovoStatus,
                campoObservacao));

        TableColumn<HistoricoCandidatura, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<HistoricoCandidatura, Integer> colunaCandidatura =
                new TableColumn<>("ID Candidatura");
        colunaCandidatura.setCellValueFactory(new PropertyValueFactory<>("idCandidatura"));

        TableColumn<HistoricoCandidatura, LocalDate> colunaData = new TableColumn<>("Data Alteracao");
        colunaData.setCellValueFactory(new PropertyValueFactory<>("dataAlteracao"));

        TableColumn<HistoricoCandidatura, String> colunaStatusAnterior =
                new TableColumn<>("Status Anterior");
        colunaStatusAnterior.setCellValueFactory(new PropertyValueFactory<>("statusAnterior"));

        TableColumn<HistoricoCandidatura, String> colunaNovoStatus = new TableColumn<>("Novo Status");
        colunaNovoStatus.setCellValueFactory(new PropertyValueFactory<>("novoStatus"));

        TableColumn<HistoricoCandidatura, String> colunaObservacao = new TableColumn<>("Observacao");
        colunaObservacao.setCellValueFactory(new PropertyValueFactory<>("observacao"));

        tabelaHistoricos.getColumns().addAll(
                colunaId,
                colunaCandidatura,
                colunaData,
                colunaStatusAnterior,
                colunaNovoStatus,
                colunaObservacao);
        tabelaHistoricos.getItems().setAll(historicoRepository.listarHistoricos());

        tabelaHistoricos.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, selecionado) -> {
                    if (selecionado != null) {
                        campoId.setEditable(false);
                        campoId.setText(String.valueOf(selecionado.getId()));
                        selecionarCandidatura(comboCandidatura, selecionado.getCandidatura());
                        campoDataAlteracao.setValue(selecionado.getDataAlteracao());
                        comboStatusAnterior.setValue(selecionado.getStatusAnterior());
                        comboNovoStatus.setValue(selecionado.getNovoStatus());
                        campoObservacao.setText(selecionado.getObservacao());
                    }
                });

        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setAlignment(Pos.CENTER);
        formulario.add(new Label("ID:"), 0, 0);
        formulario.add(campoId, 1, 0);
        formulario.add(new Label("Candidatura:"), 0, 1);
        formulario.add(comboCandidatura, 1, 1);
        formulario.add(new Label("Data de alteracao:"), 0, 2);
        formulario.add(campoDataAlteracao, 1, 2);
        formulario.add(new Label("Status anterior:"), 0, 3);
        formulario.add(comboStatusAnterior, 1, 3);
        formulario.add(new Label("Novo status:"), 0, 4);
        formulario.add(comboNovoStatus, 1, 4);
        formulario.add(new Label("Observacao:"), 0, 5);
        formulario.add(campoObservacao, 1, 5);

        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER);
        botoes.getChildren().addAll(btnCadastrar, btnAtualizar, btnLimpar, btnDeletar);

        VBox raiz = new VBox(20);
        raiz.setPadding(new Insets(20));
        raiz.setAlignment(Pos.TOP_CENTER);
        raiz.getChildren().addAll(titulo, formulario, botoes, tabelaHistoricos);

        Scene scene = new Scene(raiz, 850, 600);
        stage.setScene(scene);
        stage.show();
    }

    private HistoricoCandidatura criarHistorico(
            TextField campoId,
            ComboBox<Candidatura> comboCandidatura,
            DatePicker campoDataAlteracao,
            ComboBox<String> comboStatusAnterior,
            ComboBox<String> comboNovoStatus,
            TextField campoObservacao) throws Exception {
        int id = Integer.parseInt(campoId.getText());
        Candidatura candidatura = comboCandidatura.getValue();
        LocalDate dataAlteracao = campoDataAlteracao.getValue();
        String statusAnterior = comboStatusAnterior.getValue();
        String novoStatus = comboNovoStatus.getValue();
        String observacao = campoObservacao.getText();

        if (candidatura == null || dataAlteracao == null || statusAnterior == null
                || novoStatus == null || observacao.isBlank()) {
            throw new Exception("Todos os campos obrigatorios devem ser preenchidos.");
        }

        return new HistoricoCandidatura(
                id,
                candidatura,
                dataAlteracao,
                statusAnterior,
                novoStatus,
                observacao);
    }

    private ComboBox<String> criarComboStatus(String texto) {
        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().addAll("Enviada", "Em analise", "Aprovada", "Recusada");
        combo.setPromptText(texto);
        return combo;
    }

    private void limparCampos(
            TextField campoId,
            ComboBox<Candidatura> comboCandidatura,
            DatePicker campoDataAlteracao,
            ComboBox<String> comboStatusAnterior,
            ComboBox<String> comboNovoStatus,
            TextField campoObservacao) {
        campoId.setEditable(true);
        campoId.clear();
        comboCandidatura.setValue(null);
        campoDataAlteracao.setValue(null);
        comboStatusAnterior.setValue(null);
        comboNovoStatus.setValue(null);
        campoObservacao.clear();
    }

    private void selecionarCandidatura(ComboBox<Candidatura> combo, Candidatura selecionada) {
        if (selecionada == null) {
            combo.setValue(null);
            return;
        }

        for (Candidatura candidatura : combo.getItems()) {
            if (candidatura.getId() == selecionada.getId()) {
                combo.setValue(candidatura);
                return;
            }
        }
    }

    private void configurarComboCandidatura(ComboBox<Candidatura> combo) {
        combo.setCellFactory(param -> criarCelulaCandidatura());
        combo.setButtonCell(criarCelulaCandidatura());
    }

    private ListCell<Candidatura> criarCelulaCandidatura() {
        return new ListCell<Candidatura>() {
            @Override
            protected void updateItem(Candidatura item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("ID: " + item.getId() + " - " + item.getNomeAtleta()
                            + " - " + item.getTituloVaga());
                }
            }
        };
    }
}
