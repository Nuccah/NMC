package controller;

import java.io.File;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.ToolFactory;

/**
 * Classe permettant de convertir les fichiers multimédias au format adéquat 
 * @author Antoine Ceyssens
 *
 */
public class Converter {
	private static Converter instance = null;

	protected Converter(){

	}

	public static Converter getInstance(){
		if(instance == null){
			instance = new Converter();
		}
		return instance;
	}
	/**
	 * Convertit le fichier vidéo entré au format .mp4<br />
	 * Le fichier créé est placé dans le même dossier que celui d'origine
	 * 
	 * @param fToConvert : Fichier vidéo à convertir au format .mp4
	 * @return 
	 * @return 
	 */
	public String convertToMP4(File fToConvert){
		String newFileName = fToConvert.getName().substring(0, fToConvert.getName().lastIndexOf("."))+".mp4";
		if(fToConvert.getName() == newFileName)
			return fToConvert.getName();
		System.out.println("New File: "+newFileName);
		IMediaReader reader = ToolFactory.makeReader(fToConvert.getAbsolutePath());
		System.out.println("New Path: "+fToConvert.getParent()+Parser.getInstance().getSlash()+newFileName);
		reader.addListener(ToolFactory.makeWriter(fToConvert.getParent()+Parser.getInstance().getSlash()+newFileName, reader));
		while(reader.readPacket() == null);
		return fToConvert.getParent()+Parser.getInstance().getSlash()+newFileName;
	}

	/**
	 * Convertit le fichier audio entré au format .mp3<br />
	 * Le fichier créé est placé dans le même dossier que celui d'origine
	 * 
	 * @param fToConvert : Fichier audio à convertir au format .mp3
	 * @return 
	 */
	public String convertToMP3(File fToConvert){
		String newFileName = fToConvert.getName().substring(0, fToConvert.getName().lastIndexOf("."))+".mp3";
		if(fToConvert.getName() == newFileName)
			return fToConvert.getName();
		IMediaReader reader = ToolFactory.makeReader(fToConvert.getAbsolutePath());
		reader.addListener(ToolFactory.makeWriter(fToConvert.getParent()+Parser.getInstance().getSlash()+newFileName, reader));
		while(reader.readPacket() == null);
		return fToConvert.getParent()+Parser.getInstance().getSlash()+newFileName;
	}
}
