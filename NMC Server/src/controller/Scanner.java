package controller;

import java.io.File;
import java.util.ArrayList;

import model.Config;

/**
 * Classe de scan des fichiers multimédias
 * @author Antoine Ceyssens
 *
 */
public class Scanner implements Runnable {
	private static Scanner instance = null;
	private ArrayList<File> listFileToConvert;
	
	@Override
	public void run() {
		while(true){
			scanStock();
		}
	}
	
	protected Scanner(){
		listFileToConvert = new ArrayList<File>();
	}
	
	public static Scanner getInstance(){
		if(instance == null){
			instance = new Scanner();
		}
		return instance;
	}
	/**
	 * Scanne les fichiers multimédias à la recherche de ceux qui doivent être convertis
	 */
	public void scanStock(){
		ArrayList<File> listDir = new ArrayList<File>();
		String root = Config.getInstance().getProp("root_dir");
		String slash = Parser.getInstance().getSlash();
		File dMov = new File(root+slash+"Movies");
		File dMus = new File(root+slash+"Music");
		File dSer = new File(root+slash+"Series");
		listDir.add(dMov);
		listDir.add(dMus);
		listDir.add(dSer);
		
		for(int i = 0; i < listDir.size(); i++){
			String[] listFile = listDir.get(i).list();
			for(int j = 0; j < listFile.length; j++){
				if(Parser.getInstance().mustBeConverted(listFile[j])) {
					File fl = new File(listDir.get(i).getPath()+slash+listFile[j]);
					if(!listFileToConvert.contains(fl)) listFileToConvert.add(fl);
				}					
			}
		}
	}
}
