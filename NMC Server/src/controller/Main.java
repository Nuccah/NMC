package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import model.Config;

/**
 * Classe principale du serveur
 * <br /><br />
 * Ce programme peut être lancé en mode DEBUG afin d'afficher plus de messages d'erreurs avec la commande: --debug 
 * @author Antoine Ceyssens
 *
 */
public class Main {
	private static boolean DEBUG = false; 
	private static boolean INIT = false;
	private static boolean DEV = false;
	
	public static void main(String[] args) {
		int initInd = -1;
		for(int i = 0; i < args.length; i++){
			if(args[i].compareTo("--debug") == 0) DEBUG = true;
			if(args[i].compareTo("--dev") == 0) DEV = true;
			if(args[i].compareTo("--init") == 0){
				initInd = i;
				INIT = true;
			}
		}
		Config.getInstance();
		
		//------------- INIT ---------------
		if(INIT){
			String path = args[initInd+1];
			if(path.isEmpty()){
				System.out.println("[Error] - Path will be passed to server after --init");
				System.exit(1);
			}
			if(!Initializer.getInstance().importDefaultConf(path)){
				System.out.println("[Error] - Unable to create default configurations."
						+ "\nPlease retry and make sure path to config file is correct.");
				System.exit(1);
			}
		}
		if(Config.getInstance().getProp("url_db") == null){
			System.out.println("[Error] - Please launch the server with --init option before trying to use it");
		}
		//------------ INIT END ------------

		//---------- SOCKET ZONE -----------
		Thread sockTh = new Thread(
				new Runnable() {
					
					@Override
					public void run() {
						ServerSocket srv = null;
						ArrayList<Thread> listTh = null;
						try {
							srv = new ServerSocket(Integer.valueOf(Config.getInstance().getProp("sock_port")));
							listTh = new ArrayList<Thread>();
							do{
								Socket cl = srv.accept();
								ServerListener sl = new ServerListener(cl);
								listTh.add(new Thread(sl));
								listTh.get(listTh.size() - 1).start();
							} while(!srv.isClosed());
						} catch (NumberFormatException | IOException e) {
							e.printStackTrace();
						} finally {
							try {
								srv.close();
							} catch (IOException e) {
								System.out.println("[Warning] Server listener is closed!");
								if(DEBUG) e.printStackTrace();
							}
						}
					}
		});
		if(!DEBUG) sockTh.setDaemon(true);
		sockTh.start();
		//-------- SOCKET ZONE END --------
		
		//-------- SFTP ZONE --------------
		TransferManager.getInstance().start();
		//-------- SFTP ZONE END ----------
	}
	/**
	 * Permet de savoir si le programme tourne en mode DEBUG ou non
	 * @return Vrai si le programme tourne en DEBUG
	 */
	public static boolean getDebug(){
		return DEBUG;
	}
	
	/**
	 * Permet de savoir si le programme tourne en mode DEV ou non
	 * @return Vrai si le programme tourne en DEV
	 */
	public static boolean getDev(){
		return DEV;
	}

}
