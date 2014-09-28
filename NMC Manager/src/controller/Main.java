package controller;

import java.awt.EventQueue;

import view.Welcome;
import model.Config;
import model.Profil;

/**
 * Classe principale du programme
 * @author Antoine
 *
 */
public class Main {

	public static void main(String[] args) {
		Config.init();
		Profil.reset();
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
					Welcome wcScreen = new Welcome();
					wcScreen.setVisible(true);								
			}
		});
	}

}
