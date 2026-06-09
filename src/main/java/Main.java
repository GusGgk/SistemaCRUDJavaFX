import javafx.application.Application;
import view.MenuPrincipal;
import java.util.Locale;

public class Main {
	public static void main(String[] args) {
		Locale.setDefault(new Locale("pt", "BR"));
		Application.launch(MenuPrincipal.class, args);
	}
}
