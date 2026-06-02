package util;

import javafx.scene.control.Alert;

public class AlertaUtil {

    private static void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void mostrarSucesso(String mensagem) {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", mensagem);
    }

    public static void mostrarErro(String mensagem) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", mensagem);
    }

    public static void mostrarAviso(String mensagem) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", mensagem);
    }

    public static void erroCampoVazio() {
        mostrarErro("Todos os campos obrigatórios devem ser preenchidos.");
    }

    public static void erroNumeroInvalido() {
        mostrarErro("Os campos de ID, altura e peso devem conter apenas números.");
    }

    public static void erroArquivo() {
        mostrarErro("Erro ao salvar ou carregar os dados do arquivo.");
    }

    public static void erroRegistroNaoEncontrado(String entidade) {
        mostrarErro(entidade + " não encontrado.");
    }
}