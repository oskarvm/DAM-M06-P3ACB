package ACB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

public class ACBMain {

	public static void main(String[] args) throws IOException, SQLException, ParseException {
		Menu menu = new Menu();
		Connection conn = null;
		Identity identity;
		int option;
		int intents = 0;
		Controlador controlador = new Controlador();
		controlador.init();
		while (intents < 3 && conn == null) {
			identity = menu.autenticacio(intents);
			// prova de test
			identity.toString();
			
			conn = controlador.getConnection(identity);
			intents++;
		}

		Metodos metodos = new Metodos(conn);

		option = menu.menuPral();
		while (option > 0 && option < 11) {
			switch (option) {
			case 1:
				metodos.mostraEquips();
				break;

			case 2:
				metodos.mostraJugadorsDeUnEquip();
				break;

			case 3:
				metodos.creaEquipo();
				break;

			case 4:
				metodos.creaJugador();
				break;

			case 5:
				metodos.creaPartit();
				break;

			case 6:
				metodos.mostraJugadorsSenseEquip();
				break;

			case 7:
				metodos.assignarJugadorAUnEquip();
				break;

			case 8:
				metodos.desvincularJugadorDeUnEquip();
				break;

			case 9:
				metodos.carregaEstadistiques();
				break;

			case 10:
				metodos.sortir();
				break;

			default:
				System.out.println("Introdueixi una de les opcions anteriors");
				break;

			}
			option = menu.menuPral();
		}

	}

}
