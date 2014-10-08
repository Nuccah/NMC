package controller;

import model.Config;

/**
 * Classe principale du serveur
 * @author Antoine Ceyssens
 *
 */
public class Main {

	public static void main(String[] args) {
		Config.getInstance();
		TransferManager.getInstance().start();
	}

}
