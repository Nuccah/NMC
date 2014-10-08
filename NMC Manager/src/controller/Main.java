package controller;

import java.awt.EventQueue;

import model.Config;
import model.Profil;
import view.Welcome;

/**
 * Classe principale du programme
 * @author Antoine
 *
 */
public class Main {

	public static void main(String[] args) {
		Config.getInstance();
		Profil.getInstance().reset();
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
					Welcome wcScreen = new Welcome();
					wcScreen.setVisible(true);								
			}
		});
	}

}
