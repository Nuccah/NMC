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
		Thread sockTh = new Thread(
				new Runnable() {
					
					@Override
					public void run() {
						ServerSocket srv = null;
						try {
							srv = new ServerSocket(Integer.valueOf(Config.getInstance().getProp("sock_port")));
							ArrayList<Thread> listTh = new ArrayList<Thread>();
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
		sockTh.start();
		TransferManager.getInstance().start();
	}

}
