package controller;

import java.awt.EventQueue;
import java.awt.SystemTray;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import view.MinIcon;
import model.Config;

/**
 * Classe principale du serveur
 * <br /><br />
 * Ce programme peut être lancé en mode DEBUG afin d'afficher plus de messages d'erreurs avec la commande: --debug<br />
 * Utilisation en ligne de commande:<br />
 * &nbsp;- restart : crée une nouvelle instance du serveur et tue celle existante. <br />
 * &nbsp;- stop : tue l'instance existante.
 * @author Antoine Ceyssens & Derek Van Hove
 * @version RC2-2.5.2
 *
 */
public class Main {
	private static boolean DEBUG = false; 
	private static boolean INIT = false;
	private static boolean DEV = false;
	private static final String pidFileLocation = "server.pid";
	
	public static void main(String[] args) {
		int initInd = -1;
		for(int i = 0; i < args.length; i++){
			if(args[i].compareTo("--debug") == 0) DEBUG = true;
			if(args[i].compareTo("--dev") == 0) DEV = true;
			if(args[i].compareTo("--init") == 0){
				initInd = i;
				INIT = true;
			}
			if(args[i].compareTo("restart") == 0){
				File pidFile = new File(pidFileLocation);
				if(pidFile.exists()){
					FileReader fr = null;
					BufferedReader br = null;
					try {
						fr = new FileReader(pidFile);
						br = new BufferedReader(fr);
						String line = br.readLine();
						System.out.println("Pid = "+line);
						if(Parser.getInstance().isWindows()) Runtime.getRuntime().exec("taskkill /f /pid "+line);
					} catch (FileNotFoundException e) {
					} catch (IOException e) {
						System.out.println("[Error] - Couldn't read pid file");
						e.printStackTrace();
					} finally{
						try {
							br.close();
							fr.close();							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					pidFile.delete();
				}
				try {
					final ProcessBuilder pb = new ProcessBuilder("nmc-server");
					pb.start();
				} catch (IOException e) {
					System.out.println("[Error] - Unable to restart the nmc-server");
					if(DEBUG) e.printStackTrace();
				}
				System.out.println("[NMC Server] Restarted");
				System.exit(0);
			} 
			if(args[i].compareTo("stop") == 0){
				System.out.println("[NMC Server] Stopping");
				File pidFile = new File(pidFileLocation);
				if(pidFile.exists()){
					FileReader fr = null;
					BufferedReader br = null;
					try {
						fr = new FileReader(pidFile);
						br = new BufferedReader(fr);
						String line = br.readLine();
						System.out.println("Pid = "+line);
						if(Parser.getInstance().isWindows()) Runtime.getRuntime().exec("taskkill /f /pid "+line);
					} catch (FileNotFoundException e) {
					} catch (IOException e) {
						System.out.println("[Error] - Couldn't read pid file");
						e.printStackTrace();
					} finally{
						try {
							br.close();
							fr.close();							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					pidFile.delete();
				}
				System.out.println("[NMC Server] Properly stopped");
				System.exit(0);
			}
		}
		Config.getInstance();
		try {
			writePID(pidFileLocation);
		} catch (IOException e1) {
			System.out.println("[Error] - Couldn't create the PID file");
			if(DEBUG) e1.printStackTrace();
		}
		
		//------------ Icon Tray -----------
		if (SystemTray.isSupported()){
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					new MinIcon();	
				}
			});
		}
		//--------- Icon Tray End ----------
		
		//------------- INIT ---------------
		if(INIT){
			if(args[initInd+3] == null){
				System.out.println("[Error] - Missing arguments required");
				System.exit(1);
			}
			Initializer.getInstance().importDefaultConf(args, initInd);
			try {
				final ProcessBuilder pb = new ProcessBuilder("nmc-server");
				pb.start();
			} catch (IOException e) {
				System.out.println("[Error] - Unable to restart the nmc-server");
				if(DEBUG) e.printStackTrace();
			}
			System.exit(0);
		}
		if(Config.getInstance().getProp("url_db") == null){
			System.out.println("[Error] - Please launch the server with --init option before trying to use it");
			System.exit(1);
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
								System.out.println("[Warning] Server listener is closed!");
							} catch (IOException e) {
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
	/**
	 * Permet d'écrire le pid du serveur dans un fichier
	 * @param fileLocation : Le path du fichier où le pid doit être écrit
	 * @throws IOException : Lancée si erreur d'écriture ou si manque de droits
	 */
	public static void writePID(String fileLocation) throws IOException
	{       
		File fPid = new File(fileLocation);
		if(!fPid.exists()) fPid.createNewFile();
		else {
			fPid.delete();
			fPid.createNewFile();
		}
	    String pid = ManagementFactory.getRuntimeMXBean().getName();
	    if (pid.indexOf("@") != -1) 
	    {
	        pid = pid.substring(0, pid.indexOf("@"));
	    }                                               
	    BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation));
	    writer.write(pid);
	    writer.newLine();
	    writer.flush();
	    writer.close();                     
	}
}
