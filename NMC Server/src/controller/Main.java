package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import model.Config;

/**
 * Classe principale du serveur
 * @author Antoine Ceyssens
 *
 */
public class Main {
	
	public static void main(String[] args) {
		Config.getInstance();
		//------------- INIT ---------------
		if(args[1].compareTo("--init") == 0){
			if(args[2].isEmpty()){
				System.out.println("[Error] - Path will be passed to server after --init");
				System.exit(1);
			}
			if(!Initializer.getInstance().searchForDefaultConf(args[2])){
				System.out.println("[Error] - Unable to create default configurations");
				System.exit(1);
			}
		}
		if(Config.getInstance().getProp("db_url") == null){
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
								e.printStackTrace();
							}
						}
					}
		});
		sockTh.setDaemon(true);
		for(int i = 0; i < args.length; i++){
			if(args[i].compareTo("--debug") == 0) sockTh.setDaemon(false);
		}
		sockTh.start();
		//-------- SOCKET ZONE END --------
		
		//-------- SFTP ZONE --------------
		TransferManager.getInstance().start();
		//-------- SFTP ZONE END ----------
	}

}
