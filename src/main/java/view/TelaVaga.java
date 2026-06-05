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
import repository.VagaRepository;
import util.AlertaUtil;

public class TelaVaga extends Application {

    private VagaRepository vagaRepository;

    //construtor recebendo o repositório instanciado
    public TelaVaga(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }

    public TelaVaga() {
        this.vagaRepository = new VagaRepository();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) throws Exception {
        //visualização da tabela
        TableView<Vaga> tabelaVagas = new TableView<>();

        stage.setTitle("DRAFT - Gerenciar Vagas");
        Label titulo = new Label("Cadastro de Vagas");
        titulo.setFont(new Font("Arial", 24));

        //componentes do formulário
        TextField campoId = new TextField();
        campoId.setPromptText("ID");

        TextField campoTitulo = new TextField();
        campoTitulo.setPromptText("Título da vaga");

        TextField campoEsporte = new TextField();
        campoEsporte.setPromptText("Esporte");

        TextField campoPosicao = new TextField();
        campoPosicao.setPromptText("Posição desejada");

        TextField campoInstituicao = new TextField();
        campoInstituicao.setPromptText("Instituição ofertante");

        ComboBox<String> comboStatus = new ComboBox<>();
        comboStatus.getItems().addAll("Aberta", "Encerrada");
        comboStatus.setValue("Aberta");

        //ações dos botões
        
        //botão cadastrar
        Button btnCadastrar = new Button("Cadastrar Vaga");
        btnCadastrar.setOnAction(evento -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                String tit = campoTitulo.getText();
                String esp = campoEsporte.getText();
                String pos = campoPosicao.getText();
                String inst = campoInstituicao.getText();
                String status = comboStatus.getValue();

                //validação de preenchimento obrigatório
                if (tit.isEmpty() || esp.isEmpty() || pos.isEmpty() || inst.isEmpty()) {
                    throw new Exception("Todos os campos obrigatórios devem ser preenchidos.");
                }

                Vaga vaga = new Vaga(id, tit, esp, pos, inst, status);
                boolean sucesso = vagaRepository.adicionarVaga(vaga);

                if (sucesso) {
                    AlertaUtil.mostrarSucesso("Vaga cadastrada com sucesso!");
                    tabelaVagas.getItems().setAll(vagaRepository.listarVagas());
                    limparCampos(campoId, campoTitulo, campoEsporte, campoPosicao, campoInstituicao, comboStatus);
                } else {
                    AlertaUtil.mostrarErro("Erro ao cadastrar: ID já existente.");
                }
            } catch (NumberFormatException e) {
                AlertaUtil.erroNumeroInvalido(); //disparado quando letras são digitadas no id
            } catch (Exception e) {
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        //botão atualizar
        Button btnAtualizar = new Button("Atualizar Vaga");
        btnAtualizar.setOnAction(evento -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                String tit = campoTitulo.getText();
                String esp = campoEsporte.getText();
                String pos = campoPosicao.getText();
                String inst = campoInstituicao.getText();
                String status = comboStatus.getValue();

                if (tit.isEmpty() || esp.isEmpty() || pos.isEmpty() || inst.isEmpty()) {
                    throw new Exception("Todos os campos devem ser preenchidos!");
                }

                Vaga vaga = new Vaga(id, tit, esp, pos, inst, status);
                Vaga selecionado = tabelaVagas.getSelectionModel().getSelectedItem();
                if (selecionado == null) {
                    throw new Exception("Selecione uma vaga na tabela para atualizar.");
                }

                boolean sucesso = vagaRepository.atualizarVaga(vaga);
                if (sucesso) {
                    AlertaUtil.mostrarSucesso("Vaga atualizada com sucesso!");
                    tabelaVagas.getItems().setAll(vagaRepository.listarVagas());
                } else {
                    AlertaUtil.mostrarErro("Erro ao atualizar vaga.");
                }
            } catch (NumberFormatException e) {
                AlertaUtil.mostrarErro("O ID deve conter apenas números.");
            } catch (Exception e) {
                AlertaUtil.mostrarAviso(e.getMessage());
            }
        });

        //botão deletar
        Button btnDeletar = new Button("Deletar Vaga");
        btnDeletar.setOnAction(evento -> {
            Vaga selecionado = tabelaVagas.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                vagaRepository.deletarVaga(selecionado.getId());
                AlertaUtil.mostrarSucesso("Vaga deletada com sucesso!");
                tabelaVagas.getItems().setAll(vagaRepository.listarVagas());
                limparCampos(campoId, campoTitulo, campoEsporte, campoPosicao, campoInstituicao, comboStatus);
            } else {
                AlertaUtil.mostrarAviso("Selecione uma vaga para deletar.");
            }
        });

        //botão limpar campos
        Button btnLimpar = new Button("Limpar Campos");
        btnLimpar.setOnAction(evento -> {
            limparCampos(campoId, campoTitulo, campoEsporte, campoPosicao, campoInstituicao, comboStatus);
        });

        //configuração das colunas da tabela
        TableColumn<Vaga, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Vaga, String> colunaTitulo = new TableColumn<>("Título");
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Vaga, String> colunaEsporte = new TableColumn<>("Esporte");
        colunaEsporte.setCellValueFactory(new PropertyValueFactory<>("esporte"));

        TableColumn<Vaga, String> colunaInstituicao = new TableColumn<>("Instituição");
        colunaInstituicao.setCellValueFactory(new PropertyValueFactory<>("instituicao"));

        TableColumn<Vaga, String> colunaStatus = new TableColumn<>("Status");
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tabelaVagas.getColumns().addAll(colunaId, colunaTitulo, colunaEsporte, colunaInstituicao, colunaStatus);
        tabelaVagas.getItems().setAll(vagaRepository.listarVagas());

        //evento que escuta a seleção de linhas na tabela
        tabelaVagas.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                campoId.setEditable(false); //id não pode ser editado pois é chave primária
                campoId.setText(String.valueOf(selecionado.getId()));
                campoTitulo.setText(selecionado.getTitulo());
                campoEsporte.setText(selecionado.getEsporte());
                campoPosicao.setText(selecionado.getPosicao());
                campoInstituicao.setText(selecionado.getInstituicao());
                comboStatus.setValue(selecionado.getStatus());
            }
        });

        //montagem do layout
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setAlignment(Pos.CENTER);

        formulario.add(new Label("ID:"), 0, 0);
        formulario.add(campoId, 1, 0);

        formulario.add(new Label("Título:"), 0, 1);
        formulario.add(campoTitulo, 1, 1);

        formulario.add(new Label("Esporte:"), 0, 2);
        formulario.add(campoEsporte, 1, 2);

        formulario.add(new Label("Posição:"), 0, 3);
        formulario.add(campoPosicao, 1, 3);

        formulario.add(new Label("Instituição:"), 0, 4);
        formulario.add(campoInstituicao, 1, 4);

        formulario.add(new Label("Status:"), 0, 5);
        formulario.add(comboStatus, 1, 5);

        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER);
        botoes.getChildren().addAll(btnCadastrar, btnAtualizar, btnLimpar, btnDeletar);

        VBox raiz = new VBox(20);
        raiz.setPadding(new Insets(20));
        raiz.setAlignment(Pos.TOP_CENTER);
        raiz.getChildren().addAll(titulo, formulario, botoes, tabelaVagas);

        Scene scene = new Scene(raiz, 700, 600);
        stage.setScene(scene);
        stage.show();
    }

    //método que reseta os campos
    private void limparCampos(TextField campoId, TextField campoTitulo, TextField campoEsporte, TextField campoPosicao, TextField campoInstituicao, ComboBox<String> comboStatus) {
        campoId.setEditable(true);
        campoId.clear();
        campoTitulo.clear();
        campoEsporte.clear();
        campoPosicao.clear();
        campoInstituicao.clear();
        comboStatus.setValue("Aberta");
    }
}
