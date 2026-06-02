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
import model.PerfilEsportivo;
import repository.AtletaRepository;
import repository.PerfilAtletaRepository;
import util.AlertaUtil;

public class TelaPerfilAtleta extends Application {
    private AtletaRepository atletaRepository;
    private PerfilAtletaRepository perfilAtletaRepository;

    public TelaPerfilAtleta(AtletaRepository atletaRepository, PerfilAtletaRepository perfilAtletaRepository){
        this.atletaRepository = atletaRepository;
        this.perfilAtletaRepository = perfilAtletaRepository;
    }

    public TelaPerfilAtleta(){
        this.atletaRepository = new AtletaRepository();
        this.perfilAtletaRepository = new PerfilAtletaRepository();
    }

    @Override
    public void start(Stage stage) throws Exception {
        TableView<PerfilEsportivo> tabelaPerfilAtletas = new TableView<>();

        stage.setTitle("DRAFT - Perfil Esportivo dos Atletas");
        Label titulo = new Label("Perfil Esportivo de Atletas");
        titulo.setFont(new Font("Arial", 24));

        TextField campoIdPerfil = new TextField();
        campoIdPerfil.setPromptText("ID perfil");

        TextField campoIdAtleta = new TextField();
        campoIdAtleta.setPromptText("ID Atleta vinculado");

        TextField campoEsporte = new TextField();
        campoEsporte.setPromptText("Esporte escolhido");

        TextField campoPosicao = new TextField();
        campoPosicao.setPromptText("Posição (Para esportes coletivos)");

        TextField campoAltura = new TextField();
        campoAltura.setPromptText("Altura (cm)");

        TextField campoPeso = new TextField();
        campoPeso.setPromptText("Peso (Kg)");

        TextField campoMaoDominante = new TextField();
        campoMaoDominante.setPromptText("Mão Dominante (Canhoto ou destro)");

        TextField campoPeDominante = new TextField();
        campoPeDominante.setPromptText("Pé Dominante (Canhoto ou destro)");

        TextField campoBio = new TextField();
        campoBio.setPromptText("Biografia");

        Button btnCadastrar = new Button("Cadastrar Perfil Esportivo de Atleta");
        btnCadastrar.setOnAction(evento ->{
            try{
                int id = Integer.parseInt(campoIdPerfil.getText());
                int idAtleta = Integer.parseInt(campoIdAtleta.getText());
                String esporte = campoEsporte.getText();
                String posicao = campoPosicao.getText();
                String maoDominante = campoMaoDominante.getText();
                String peDominante = campoPeDominante.getText();
                String bio = campoBio.getText();

                if(esporte.isEmpty()||posicao.isEmpty()||maoDominante.isEmpty()||peDominante.isEmpty()||bio.isEmpty()){
                    AlertaUtil.erroCampoVazio();
                    throw new Exception("Todos os campos devem ser preenchidos");
                }

                Double altura = Double.parseDouble(campoAltura.getText());
                Double peso = Double.parseDouble(campoPeso.getText());

                if(altura <=0 || peso <= 0){
                    AlertaUtil.mostrarErro("Altura e peso devem ser maiores que zero.");
                    throw new Exception("Altura e peso devem ser maiores que zero");
                }

                Atleta atleta = atletaRepository.buscarPorIdAtleta(idAtleta);
                if(atleta == null){
                    AlertaUtil.mostrarAviso("Atleta não encontrado. Verifique se o id do atleta já foi criado!");
                    throw new Exception("Atleta não encontrado. Cadastre o atleta antes de criar o perfil");
                }

                PerfilEsportivo perfil = new PerfilEsportivo(
                        id,
                        atleta,
                        esporte,
                        posicao,
                        altura,
                        peso,
                        maoDominante,
                        peDominante,
                        bio
                );
                boolean sucesso = perfilAtletaRepository.adicionarPerfil(perfil);
                if(sucesso){
                    AlertaUtil.mostrarSucesso("Perfil esportivo cadastrado com sucesso!");
                    tabelaPerfilAtletas.getItems().setAll(perfilAtletaRepository.listarPerfis());
                } else{
                    AlertaUtil.mostrarErro("Erro ao cadastrar. Verifique se esse email ou atleta já possui cadastro");
                }

                tabelaPerfilAtletas.getItems().setAll(perfilAtletaRepository.listarPerfis());

            } catch (NumberFormatException e){
                AlertaUtil.erroNumeroInvalido();
            } catch (Exception e) {
                AlertaUtil.mostrarErro(e.getMessage());            }
        });

        Button btnAtualizar = new Button("Atualizar Perfil Esportivo de Atleta");
        btnAtualizar.setOnAction(evento ->{
            try{
                int id = Integer.parseInt(campoIdPerfil.getText());
                int idAtleta = Integer.parseInt(campoIdAtleta.getText());

                String esporte = campoEsporte.getText();
                String posicao = campoPosicao.getText();
                String maoDominante = campoMaoDominante.getText();
                String peDominante = campoPeDominante.getText();
                String bio = campoBio.getText();

                Double altura = Double.parseDouble(campoAltura.getText());
                Double peso = Double.parseDouble(campoPeso.getText());
                if(altura <=0 || peso <= 0){
                    AlertaUtil.mostrarErro("Altura e peso devem ser maiores que zero.");
                    throw new Exception("Altura e peso devem ser maiores que zero");
                }

                Atleta atleta = atletaRepository.buscarPorIdAtleta(idAtleta);
                if(atleta == null){
                    AlertaUtil.mostrarAviso("Atleta não encontrado. Verifique se o id do atleta já foi criado!");
                    throw new Exception("Atleta não encontrado. Cadastre o atleta antes de criar o perfil");
                }
                PerfilEsportivo perfil = new PerfilEsportivo(
                        id,
                        atleta,
                        esporte,
                        posicao,
                        altura,
                        peso,
                        maoDominante,
                        peDominante,
                        bio
                );
                PerfilEsportivo selecionado = tabelaPerfilAtletas.getSelectionModel().getSelectedItem();

                if (selecionado == null) {
                    throw new Exception("Selecione um perfil para atualizar.");
                }

                boolean sucesso = perfilAtletaRepository.atualizarPerfil(perfil);

                if (sucesso) {
                    AlertaUtil.mostrarSucesso("Perfil esportivo atualizado com sucesso!");
                    tabelaPerfilAtletas.getItems().setAll(perfilAtletaRepository.listarPerfis());
                } else {
                    AlertaUtil.mostrarErro("Erro ao atualizar perfil. Verifique se o perfil existe.");
                }

            } catch (NumberFormatException e){
                AlertaUtil.erroNumeroInvalido();
            } catch (Exception e){
                AlertaUtil.mostrarErro(e.getMessage());
            }
        });

        Button btnLimpar = new Button("Limpar campos");
        btnLimpar.setOnAction(evento ->{
            campoIdPerfil.setEditable(true);
            campoIdPerfil.clear();
            campoIdAtleta.clear();
            campoEsporte.clear();
            campoPosicao.clear();
            campoAltura.clear();
            campoPeso.clear();
            campoBio.clear();
            campoMaoDominante.clear();
            campoPeDominante.clear();
        });

        Button btnDeletar = new Button("Deletar Perfil");
        btnDeletar.setOnAction(evento->{
            PerfilEsportivo perfilSelecionado = tabelaPerfilAtletas.getSelectionModel().getSelectedItem();
            if(perfilSelecionado != null){
                perfilAtletaRepository.deletarPerfil(perfilSelecionado.getId());
                AlertaUtil.mostrarSucesso("Perfil Esportivo deletado com sucesso!");
                tabelaPerfilAtletas.getItems().setAll(perfilAtletaRepository.listarPerfis());
            } else {
                AlertaUtil.mostrarErro("Selecione um perfil para deletar");
            }
        });

        TableColumn<PerfilEsportivo, Integer> colunaIdPerfil = new TableColumn<>("ID perfil");
        colunaIdPerfil.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PerfilEsportivo, Integer> colunaIdAtleta = new TableColumn<>("ID atleta");
        colunaIdAtleta.setCellValueFactory(new PropertyValueFactory<>("idAtleta"));

        TableColumn<PerfilEsportivo, String> colunaNomeAtleta = new TableColumn<>("Nome Atleta:");
        colunaNomeAtleta.setCellValueFactory(new PropertyValueFactory<>("nomeAtleta"));

        TableColumn<PerfilEsportivo, String> colunaEsporte = new TableColumn<>("Esporte");
        colunaEsporte.setCellValueFactory(new PropertyValueFactory<>("esporte"));

        TableColumn<PerfilEsportivo, Double> colunaAltura = new TableColumn<>("Altura(cm)");
        colunaAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));

        TableColumn<PerfilEsportivo, Double> colunaPeso = new TableColumn<>("Peso(kg)");
        colunaPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));

        TableColumn<PerfilEsportivo, String> colunaBio = new TableColumn<>("Biografia");
        colunaBio.setCellValueFactory(new PropertyValueFactory<>("bio"));

        tabelaPerfilAtletas.getColumns().addAll(colunaIdPerfil,colunaIdAtleta,colunaNomeAtleta,colunaEsporte,colunaAltura,colunaPeso,colunaBio);
        tabelaPerfilAtletas.getItems().setAll(perfilAtletaRepository.listarPerfis());
        tabelaPerfilAtletas.getSelectionModel().selectedItemProperty().addListener((
                obs,perfilAntigo,perfilSelecionado)->{
            if(perfilSelecionado != null){
                    campoIdPerfil.setEditable(false);
                   campoIdPerfil.setText(String.valueOf(perfilSelecionado.getId()));
                   campoIdAtleta.setText(String.valueOf(perfilSelecionado.getIdAtleta()));
                   campoEsporte.setText(perfilSelecionado.getEsporte());
                   campoAltura.setText(String.valueOf(perfilSelecionado.getAltura()));
                   campoPeso.setText(String.valueOf(perfilSelecionado.getPeso()));
                   campoPosicao.setText(perfilSelecionado.getPosicao());
                   campoMaoDominante.setText(perfilSelecionado.getMaoDominante());
                   campoPeDominante.setText(perfilSelecionado.getPeDominante());
                   campoBio.setText(perfilSelecionado.getBio());
            }
                }
                );
                GridPane formulario = new GridPane();
                formulario.setHgap(10);
                formulario.setVgap(10);
                formulario.setAlignment(Pos.CENTER);

                formulario.add(new Label("ID:"), 0,0);
                formulario.add(campoIdPerfil,1,0);

                formulario.add(new Label("ID Atleta:"),0,1);
                formulario.add(campoIdAtleta,1,1);

                formulario.add(new Label("Esporte:"), 0, 2);
                formulario.add(campoEsporte, 1, 2);

                formulario.add(new Label("Posição:"), 0, 3);
                formulario.add(campoPosicao, 1, 3);

                formulario.add(new Label("Altura:"), 0, 4);
                formulario.add(campoAltura, 1, 4);

                formulario.add(new Label("Peso:"), 0, 5);
                formulario.add(campoPeso, 1, 5);

                formulario.add(new Label("Mão dominante:"), 0, 6);
                formulario.add(campoMaoDominante, 1, 6);

                formulario.add(new Label("Pé dominante:"), 0, 7);
                formulario.add(campoPeDominante, 1, 7);

                formulario.add(new Label("Bio:"), 0, 8);
                 formulario.add(campoBio, 1, 8);

        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER);
        botoes.getChildren().addAll(btnCadastrar, btnAtualizar, btnDeletar, btnLimpar);

        VBox raiz = new VBox(20);
        raiz.setPadding(new Insets(20));
        raiz.setAlignment(Pos.TOP_CENTER);
        raiz.getChildren().addAll(titulo, formulario, botoes, tabelaPerfilAtletas);

        Scene scene = new Scene(raiz, 900, 700);
        stage.setScene(scene);
        stage.show();
    }
}
