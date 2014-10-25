package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.Config;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;


/**
 * Classe de gestion de la réception des fichiers médias
 * @author Antoine Ceyssens
 *
 */
public class TransferManager extends Thread {
	private static TransferManager instance = null;

	@Override
	public void run(){
		FtpServerFactory srvFact = new FtpServerFactory();
		ListenerFactory factory = new ListenerFactory();
		
		factory.setPort(Integer.valueOf(Config.getInstance().getProp("ftp_port")));
		
		srvFact.addListener("default", factory.createListener());
		
		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
		File usProp = new File("users.properties");
		
		try {
			if(!usProp.exists()) usProp.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		userManagerFactory.setFile(usProp);
		UserManager userManager = userManagerFactory.createUserManager();
		BaseUser bu = new BaseUser();
		bu.setName(Config.getInstance().getProp("ftp_user"));
		bu.setPassword(Config.getInstance().getProp("ftp_pass"));
		java.util.List<Authority> auths = new ArrayList<Authority>();
		Authority writeP = new WritePermission();
		auths.add(writeP);
		bu.setAuthorities(auths);
		bu.setHomeDirectory(Config.getInstance().getProp("root_dir"));

		
		
		try {
			userManager.save(bu);
			srvFact.setUserManager(userManager);
			FtpServer server = srvFact.createServer();
			
			server.start();
		} catch (FtpException e) {
			e.printStackTrace();
		}
		
	}
	
	public static TransferManager getInstance(){
		if(instance == null){
			instance = new TransferManager();
		}
		return instance;
	}
}
