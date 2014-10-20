package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import model.Config;

public class MetaReciever implements Runnable {
	private ServerSocket srv;
	private ArrayList<Socket> listClient;
	
	public MetaReciever() throws NumberFormatException, IOException {
		srv = new ServerSocket(Integer.valueOf(Config.getInstance().getProp("meta_port")));
	}
	
	//TODO: Choose how to get the meta data
	
	@Override
	public void run() {
		
	}

}
