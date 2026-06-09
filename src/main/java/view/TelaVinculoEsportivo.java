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
import model.Instituicao;
import model.VinculoEsportivo;
import repository.AtletaRepository;
import repository.InstituicaoRepository;
import repository.VinculoEsportivoRepository;
import util.AlertaUtil;

import java.time.LocalDate;

public class TelaVinculoEsportivo extends Application {

    private VinculoEsportivoRepository vinculoRepository;
    private AtletaRepository atletaRepository;
    private InstituicaoRepository instituicaoRepository;

    public TelaVinculoEsportivo(
            AtletaRepository atletaRepository,
            InstituicaoRepository instituicaoRepository,
            VinculoEsportivoRepository vinculoRepository) {
        this.atletaRepository = atletaRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.vinculoRepository = vinculoRepository;
    }

    public TelaVinculoEsportivo() {
        this.atletaRepository = new AtletaRepository();
        this.instituicaoRepository = new InstituicaoRepository();
        this.vinculoRepository = new VinculoEsportivoRepository();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) throws Exception {
        TableView<VinculoEsportivo> tabelaVinculos = new TableView<>();

        stage.setTitle("DRAFT - TELA VÍNCULO ESPORTIVO");
        Label titulo = new Label("Vínculos Esportivos (Atleta <-> Instituição)");
        titulo.setFont(new Font("Arial", 22));

        TextField campoId = new TextField();
        campoId.setPromptText("ID do Vínculo");

        ComboBox<Atleta> comboAtleta = new ComboBox<>();
        comboAtleta.getItems().addAll(atletaRepository.listarAtletas());
        comboAtleta.setPromptText("Selecione um Atleta");
        // Custom rendering for Atleta in ComboBox
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

        ComboBox<Instituicao> comboInstituicao = new ComboBox<>();
        comboInstituicao.getItems().addAll(instituicaoRepository.listarInstituicoes());
        comboInstituicao.setPromptText("Selecione uma Instituição");
        // Custom rendering for Instituicao in ComboBox
        comboInstituicao.setCellFactory(param -> new ListCell<Instituicao>() {
            @Override
            protected void updateItem(Instituicao item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " (ID: " + item.getId() + ")");
                }
            }
        });
        comboInstituicao.setButtonCell(new ListCell<Instituicao>() {
            @Override
            protected void updateItem(Instituicao item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " (ID: " + item.getId() + ")");
                }
            }
        });

        DatePicker campoDataInicio = new DatePicker();
        campoDataInicio.setPromptText("Data de Início");

        ComboBox<String> comboStatus = new ComboBox<>();
        comboStatus.getItems().addAll("Ativo", "Pendente", "Encerrado");
        comboStatus.setPromptText("Status");

        Button btnCadastrar = new Button("Criar Vínculo");
        btnCadastrar.setOnAction(evento -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                Atleta atleta = comboAtleta.getValue();
                Instituicao instituicao = comboInstituicao.getValue();
                LocalDate dataInicio = campoDataInicio.getValue();
                String status = comboStatus.getValue();

                if (atleta == null || instituicao == null || dataInicio == null || status == null || status.isEmpty()) {
                    throw new Exception("Todos os campos obrigatórios devem ser preenchidos.");
                }

                VinculoEsportivo vinculo = new VinculoEsportivo(id, atleta, instituicao, dataInicio, status);
                boolean sucesso = vinculoRepository.adicionarVinculo(vinculo);

                if (sucesso) {
                    AlertaUtil.mostrarSucesso("Vínculo criado com sucesso!");
                    tabelaVinculos.getItems().setAll(vinculoRepository.listarVinculos());
                } else {
                    AlertaUtil.mostrarErro("Erro ao criar vínculo (verifique se o ID já existe).");
                }
            } catch (NumberFormatException e) {
                AlertaUtil.mostrarErro("O campo ID deve ser preenchido com um número.");
            } catch (Exception e) {
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        Button btnAtualizar = new Button("Atualizar Vínculo");
        btnAtualizar.setOnAction(evento -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                Atleta atleta = comboAtleta.getValue();
                Instituicao instituicao = comboInstituicao.getValue();
                LocalDate dataInicio = campoDataInicio.getValue();
                String status = comboStatus.getValue();

                if (atleta == null || instituicao == null || dataInicio == null || status == null || status.isEmpty()) {
                    throw new Exception("Todos os campos obrigatórios devem ser preenchidos.");
                }

                VinculoEsportivo selecionado = tabelaVinculos.getSelectionModel().getSelectedItem();
                if (selecionado == null) {
                    throw new Exception("Selecione um vínculo na tabela para atualizar.");
                }

                VinculoEsportivo vinculo = new VinculoEsportivo(id, atleta, instituicao, dataInicio, status);
                boolean sucesso = vinculoRepository.atualizarVinculo(vinculo);

                if (sucesso) {
                    AlertaUtil.mostrarSucesso("Vínculo atualizado com sucesso!");
                    tabelaVinculos.getItems().setAll(vinculoRepository.listarVinculos());
                } else {
                    AlertaUtil.mostrarErro("Erro ao atualizar vínculo.");
                }
            } catch (NumberFormatException e) {
                AlertaUtil.mostrarErro("O campo ID deve ser preenchido com um número.");
            } catch (Exception e) {
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        Button btnDeletar = new Button("Deletar Vínculo");
        btnDeletar.setOnAction(evento -> {
            VinculoEsportivo selecionado = tabelaVinculos.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                vinculoRepository.deletarVinculo(selecionado.getId());
                AlertaUtil.mostrarSucesso("Vínculo deletado com sucesso!");
                tabelaVinculos.getItems().setAll(vinculoRepository.listarVinculos());
            } else {
                AlertaUtil.mostrarAviso("Selecione um vínculo para deletar.");
            }
        });

        Button btnLimpar = new Button("Limpar Campos");
        btnLimpar.setOnAction(evento -> {
            campoId.setEditable(true);
            campoId.clear();
            comboAtleta.setValue(null);
            comboInstituicao.setValue(null);
            campoDataInicio.setValue(null);
            comboStatus.setValue(null);
        });

        TableColumn<VinculoEsportivo, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<VinculoEsportivo, String> colunaAtleta = new TableColumn<>("Atleta");
        colunaAtleta.setCellValueFactory(new PropertyValueFactory<>("nomeAtleta"));

        TableColumn<VinculoEsportivo, String> colunaInstituicao = new TableColumn<>("Instituição");
        colunaInstituicao.setCellValueFactory(new PropertyValueFactory<>("nomeInstituicao"));

        TableColumn<VinculoEsportivo, LocalDate> colunaData = new TableColumn<>("Data Início");
        colunaData.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));

        TableColumn<VinculoEsportivo, String> colunaStatus = new TableColumn<>("Status");
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tabelaVinculos.getColumns().addAll(colunaId, colunaAtleta, colunaInstituicao, colunaData, colunaStatus);
        tabelaVinculos.getItems().setAll(vinculoRepository.listarVinculos());

        tabelaVinculos.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                campoId.setEditable(false);
                campoId.setText(String.valueOf(selecionado.getId()));
                
                // Encontrar e selecionar Atleta correspondente
                for (Atleta atl : comboAtleta.getItems()) {
                    if (atl.getId() == selecionado.getAtleta().getId()) {
                        comboAtleta.setValue(atl);
                        break;
                    }
                }
                
                // Encontrar e selecionar Instituicao correspondente
                for (Instituicao inst : comboInstituicao.getItems()) {
                    if (inst.getId() == selecionado.getInstituicao().getId()) {
                        comboInstituicao.setValue(inst);
                        break;
                    }
                }
                
                campoDataInicio.setValue(selecionado.getDataInicio());
                comboStatus.setValue(selecionado.getStatus());
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

        formulario.add(new Label("Instituição:"), 0, 2);
        formulario.add(comboInstituicao, 1, 2);

        formulario.add(new Label("Data Início:"), 0, 3);
        formulario.add(campoDataInicio, 1, 3);

        formulario.add(new Label("Status:"), 0, 4);
        formulario.add(comboStatus, 1, 4);

        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER);
        botoes.getChildren().addAll(btnCadastrar, btnAtualizar, btnLimpar, btnDeletar);

        VBox raiz = new VBox(20);
        raiz.setPadding(new Insets(20));
        raiz.setAlignment(Pos.TOP_CENTER);
        raiz.getChildren().addAll(titulo, formulario, botoes, tabelaVinculos);

        Scene scene = new Scene(raiz, 650, 600);
        stage.setScene(scene);
        stage.show();
    }
}
