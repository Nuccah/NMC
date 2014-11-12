package controller;

import java.awt.EventQueue;

import controller.SocketManager;
import model.Config;
import model.Profil;
import view.IpAsk;
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
		//------------ IP Retrieve ----------
		if(Config.getInstance().getProp("srv_url") == null){
			EventQueue.invokeLater(new Runnable(){

				@Override
				public void run() {
					IpAsk iAScreen = new IpAsk();
					iAScreen.setVisible(true);
				}				
			});
		}
		else{
			//------------ Auto Config ----------
			while(Config.getInstance().getProp("init").compareTo("0") == 0){
				SocketManager.getInstance().getConfig();
			}
			//---------- Auto Config End---------
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					Welcome wcScreen = new Welcome();
					wcScreen.setVisible(true);								
				}
			});	
		}
		//---------- IP Retrieve End --------
	}

}
