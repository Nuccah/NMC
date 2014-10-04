package controller;
/**
 * Classe principale du serveur
 * @author Antoine Ceyssens
 *
 */
public class Main {

	public static void main(String[] args) {
		TransferManager.getInstance().start();
	}

}
