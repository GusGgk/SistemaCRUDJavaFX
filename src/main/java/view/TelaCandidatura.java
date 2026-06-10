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
import model.Candidatura;
import repository.CandidaturaRepository;

import util.AlertaUtil;
import java.time.LocalDate;

import javax.swing.text.TableView;

public class TelaCandidatura extends Application {

    private CandidaturaRepository candidaturaRepository;
    public TelaCandidatura(CandidaturaRepository candidaturaRepository) {this.candidaturaRepository = candidaturaRepository; }

    public TelaCandidatura(){ this.candidaturaRepository = new CandidaturaRepository(); }

    @Override
    public void start(Stage stage) throws Exception {
        TableView<Candidatura> tabelaCandidaturas = new TableView<>();
    }
}
