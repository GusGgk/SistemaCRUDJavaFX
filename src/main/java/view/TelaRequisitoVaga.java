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
import model.Vaga;
import model.RequisitoVaga;
import repository.VagaRepository;
import repository.RequisitoVagaRepository;
import util.AlertaUtil;

public class TelaRequisitoVaga extends Application {

    private VagaRepository vagaRepository;
    private RequisitoVagaRepository requisitoRepository;

    //construtor recebendo o repositório instanciado
    public TelaRequisitoVaga(VagaRepository vagaRepository, RequisitoVagaRepository requisitoRepository) {
        this.vagaRepository = vagaRepository;
        this.requisitoRepository = requisitoRepository;
    }

    public TelaRequisitoVaga() {
        this.vagaRepository = new VagaRepository();
        this.requisitoRepository = new RequisitoVagaRepository();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) throws Exception {
        //visualização da tabela
        TableView<RequisitoVaga> tabelaRequisitos = new TableView<>();

        stage.setTitle("DRAFT - Requisitos das Vagas");
        Label titulo = new Label("Requisitos de Vagas");
        titulo.setFont(new Font("Arial", 24));

        //componentes do formulário
        TextField campoIdReq = new TextField();
        campoIdReq.setPromptText("ID do Requisito");

        TextField campoIdVaga = new TextField();
        campoIdVaga.setPromptText("ID da Vaga vinculada");

        TextField campoIdadeMin = new TextField();
        campoIdadeMin.setPromptText("Idade Mínima");

        TextField campoIdadeMax = new TextField();
        campoIdadeMax.setPromptText("Idade Máxima");

        TextField campoNivelTecnico = new TextField();
        campoNivelTecnico.setPromptText("Nível Técnico (Ex: Profissional)");

        TextField campoDisponibilidade = new TextField();
        campoDisponibilidade.setPromptText("Disponibilidade (Ex: Parcial)");

        TextArea campoObservacoes = new TextArea();
        campoObservacoes.setPromptText("Outras observações...");
        campoObservacoes.setPrefRowCount(3);

        //ações dos botões
        
        //botão cadastrar
        Button btnCadastrar = new Button("Cadastrar Requisito");
        btnCadastrar.setOnAction(evento -> {
            try {
                int idReq = Integer.parseInt(campoIdReq.getText());
                int idVaga = Integer.parseInt(campoIdVaga.getText());
                int idMin = Integer.parseInt(campoIdadeMin.getText());
                int idMax = Integer.parseInt(campoIdadeMax.getText());
                String nivel = campoNivelTecnico.getText();
                String disp = campoDisponibilidade.getText();
                String obs = campoObservacoes.getText();

                //validação de preenchimento obrigatório
                if (nivel.isEmpty() || disp.isEmpty()) {
                    throw new Exception("Preencha todos os campos obrigatórios.");
                }

                if (idMin > idMax) {
                    throw new Exception("A idade mínima não pode ser maior que a máxima.");
                }

                //verifica se a vaga vinculada existe no repositório de vagas
                Vaga vaga = vagaRepository.buscarPorIdVaga(idVaga);
                if (vaga == null) {
                    AlertaUtil.mostrarAviso("Vaga não encontrada! Por favor, crie a vaga correspondente primeiro.");
                    throw new Exception("Vaga vinculada não cadastrada.");
                }

                RequisitoVaga req = new RequisitoVaga(idReq, vaga, idMin, idMax, nivel, disp, obs);
                boolean sucesso = requisitoRepository.adicionarRequisito(req);

                if (sucesso) {
                    AlertaUtil.mostrarSucesso("Requisitos cadastrados com sucesso!");
                    tabelaRequisitos.getItems().setAll(requisitoRepository.listarRequisitos());
                    limparCampos(campoIdReq, campoIdVaga, campoIdadeMin, campoIdadeMax, campoNivelTecnico, campoDisponibilidade, campoObservacoes);
                } else {
                    AlertaUtil.mostrarErro("Erro: ID de requisito já existe ou a vaga já tem requisitos associados.");
                }
            } catch (NumberFormatException e) {
                AlertaUtil.erroNumeroInvalido(); //disparado quando letras são digitadas no id
            } catch (Exception e) {
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        //botão atualizar
        Button btnAtualizar = new Button("Atualizar Requisito");
        btnAtualizar.setOnAction(evento -> {
            try {
                int idReq = Integer.parseInt(campoIdReq.getText());
                int idVaga = Integer.parseInt(campoIdVaga.getText());
                int idMin = Integer.parseInt(campoIdadeMin.getText());
                int idMax = Integer.parseInt(campoIdadeMax.getText());
                String nivel = campoNivelTecnico.getText();
                String disp = campoDisponibilidade.getText();
                String obs = campoObservacoes.getText();

                if (nivel.isEmpty() || disp.isEmpty()) {
                    throw new Exception("Preencha todos os campos!");
                }

                Vaga vaga = vagaRepository.buscarPorIdVaga(idVaga);
                if (vaga == null) {
                    throw new Exception("Vaga não encontrada.");
                }

                RequisitoVaga req = new RequisitoVaga(idReq, vaga, idMin, idMax, nivel, disp, obs);
                RequisitoVaga selecionado = tabelaRequisitos.getSelectionModel().getSelectedItem();

                if (selecionado == null) {
                    throw new Exception("Selecione um requisito na tabela para atualizar.");
                }

                boolean sucesso = requisitoRepository.atualizarRequisito(req);
                if (sucesso) {
                    AlertaUtil.mostrarSucesso("Requisito atualizado com sucesso!");
                    tabelaRequisitos.getItems().setAll(requisitoRepository.listarRequisitos());
                } else {
                    AlertaUtil.mostrarErro("Erro ao atualizar requisito.");
                }
            } catch (NumberFormatException e) {
                AlertaUtil.erroNumeroInvalido();
            } catch (Exception e) {
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        //botão deletar
        Button btnDeletar = new Button("Deletar Requisito");
        btnDeletar.setOnAction(evento -> {
            RequisitoVaga selecionado = tabelaRequisitos.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                requisitoRepository.deletarRequisito(selecionado.getId());
                AlertaUtil.mostrarSucesso("Requisito deletado com sucesso!");
                tabelaRequisitos.getItems().setAll(requisitoRepository.listarRequisitos());
                limparCampos(campoIdReq, campoIdVaga, campoIdadeMin, campoIdadeMax, campoNivelTecnico, campoDisponibilidade, campoObservacoes);
            } else {
                AlertaUtil.mostrarErro("Selecione um registro na tabela para deletar.");
            }
        });

        //botão limpar campos
        Button btnLimpar = new Button("Limpar Campos");
        btnLimpar.setOnAction(evento -> {
            limparCampos(campoIdReq, campoIdVaga, campoIdadeMin, campoIdadeMax, campoNivelTecnico, campoDisponibilidade, campoObservacoes);
        });

        //configuração das colunas da tabela
        TableColumn<RequisitoVaga, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<RequisitoVaga, Integer> colunaIdVaga = new TableColumn<>("ID Vaga");
        colunaIdVaga.setCellValueFactory(new PropertyValueFactory<>("idVaga")); // invoca getIdVaga()

        TableColumn<RequisitoVaga, String> colunaTituloVaga = new TableColumn<>("Vaga");
        colunaTituloVaga.setCellValueFactory(new PropertyValueFactory<>("tituloVaga")); // invoca getTituloVaga()

        TableColumn<RequisitoVaga, Integer> colunaIdadeMin = new TableColumn<>("Idade Min");
        colunaIdadeMin.setCellValueFactory(new PropertyValueFactory<>("idadeMinima"));

        TableColumn<RequisitoVaga, Integer> colunaIdadeMax = new TableColumn<>("Idade Max");
        colunaIdadeMax.setCellValueFactory(new PropertyValueFactory<>("idadeMaxima"));

        TableColumn<RequisitoVaga, String> colunaNivel = new TableColumn<>("Nível Técnico");
        colunaNivel.setCellValueFactory(new PropertyValueFactory<>("nivelTecnico"));

        tabelaRequisitos.getColumns().addAll(colunaId, colunaIdVaga, colunaTituloVaga, colunaIdadeMin, colunaIdadeMax, colunaNivel);
        tabelaRequisitos.getItems().setAll(requisitoRepository.listarRequisitos());

        //evento que escuta a seleção de linhas na tabela
        tabelaRequisitos.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                campoIdReq.setEditable(false);
                campoIdReq.setText(String.valueOf(selecionado.getId()));
                campoIdVaga.setText(String.valueOf(selecionado.getIdVaga()));
                campoIdadeMin.setText(String.valueOf(selecionado.getIdadeMinima()));
                campoIdadeMax.setText(String.valueOf(selecionado.getIdadeMaxima()));
                campoNivelTecnico.setText(selecionado.getNivelTecnico());
                campoDisponibilidade.setText(selecionado.getDisponibilidade());
                campoObservacoes.setText(selecionado.getObservacoes());
            }
        });

        //montagem do layout
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setAlignment(Pos.CENTER);

        formulario.add(new Label("ID Requisito:"), 0, 0);
        formulario.add(campoIdReq, 1, 0);

        formulario.add(new Label("ID Vaga Vinculada:"), 0, 1);
        formulario.add(campoIdVaga, 1, 1);

        formulario.add(new Label("Idade Mínima:"), 0, 2);
        formulario.add(campoIdadeMin, 1, 2);

        formulario.add(new Label("Idade Máxima:"), 0, 3);
        formulario.add(campoIdadeMax, 1, 3);

        formulario.add(new Label("Nível Técnico:"), 0, 4);
        formulario.add(campoNivelTecnico, 1, 4);

        formulario.add(new Label("Disponibilidade:"), 0, 5);
        formulario.add(campoDisponibilidade, 1, 5);

        formulario.add(new Label("Observações:"), 0, 6);
        formulario.add(campoObservacoes, 1, 6);

        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER);
        botoes.getChildren().addAll(btnCadastrar, btnAtualizar, btnLimpar, btnDeletar);

        VBox raiz = new VBox(20);
        raiz.setPadding(new Insets(20));
        raiz.setAlignment(Pos.TOP_CENTER);
        raiz.getChildren().addAll(titulo, formulario, botoes, tabelaRequisitos);

        Scene scene = new Scene(raiz, 800, 650);
        stage.setScene(scene);
        stage.show();
    }

    //método que reseta os campos
    private void limparCampos(TextField campoIdReq, TextField campoIdVaga, TextField campoIdadeMin, TextField campoIdadeMax, TextField campoNivelTecnico, TextField campoDisponibilidade, TextArea campoObservacoes) {
        campoIdReq.setEditable(true);
        campoIdReq.clear();
        campoIdVaga.clear();
        campoIdadeMin.clear();
        campoIdadeMax.clear();
        campoNivelTecnico.clear();
        campoDisponibilidade.clear();
        campoObservacoes.clear();
    }
}
