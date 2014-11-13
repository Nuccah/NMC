package controller;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.UIManager;

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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.getLookAndFeelDefaults().put("Panel.background", Color.WHITE);
			UIManager.put("OptionPane.background",Color.WHITE);
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}
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
