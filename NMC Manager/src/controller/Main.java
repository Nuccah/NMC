package controller;

import java.awt.EventQueue;

import controller.SocketManager;
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
		//---------- Zone de config auto temporaire ---------
		while(Config.getInstance().getProp("init").compareTo("0") == 0){
			SocketManager.getInstance().getConfig();
		}
		//---------- Zone de config auto temporaire ---------
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
					Welcome wcScreen = new Welcome();
					wcScreen.setVisible(true);								
			}
		});			
	}

}
