package controller;

import java.io.File;

import model.AudioCollector;
import model.EpisodeCollector;
import model.MetaDataCollector;
import model.VideoCollector;

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
	 * @param mdc le type et les metadonnées du fichier a convertir
	 * @param fToConvert : Fichier vidéo à convertir au format .mp4
	 * @return le nouveau nom si il a été converti sinon le nom d'origine
	 */
	public String convertToMP4(MetaDataCollector mdc, File fToConvert){
		String newFileName = fToConvert.getName().substring(0, fToConvert.getName().lastIndexOf("."))+".mp4";
		if(fToConvert.getName().equals(newFileName))
			return fToConvert.getParent()+Parser.getInstance().getSlash()+fToConvert.getName();
		IMediaReader reader = ToolFactory.makeReader(fToConvert.getAbsolutePath());
		reader.addListener(ToolFactory.makeWriter(fToConvert.getParent()+Parser.getInstance().getSlash()+newFileName, reader));
		while(reader.readPacket() == null)
			
		if (mdc instanceof EpisodeCollector)
			((EpisodeCollector)mdc).setFilename(newFileName);
		else
			((VideoCollector)mdc).setFilename(newFileName);
		return fToConvert.getParent()+Parser.getInstance().getSlash()+newFileName;
	}

	/**
	 * Convertit le fichier audio entré au format .mp3<br />
	 * Le fichier créé est placé dans le même dossier que celui d'origine
	 * @param mdc les metadonnées du fichier a convertir
	 * @param fToConvert : Fichier audio à convertir au format .mp3
	 * @return le nouveau nom si il a été converti sinon le nom d'origine
	 */
	public String convertToMP3(AudioCollector mdc, File fToConvert){
		String newFileName = fToConvert.getName().substring(0, fToConvert.getName().lastIndexOf("."))+".mp3";
		if(fToConvert.getName().equals(newFileName))
			return fToConvert.getParent()+Parser.getInstance().getSlash()+fToConvert.getName();
		IMediaReader reader = ToolFactory.makeReader(fToConvert.getAbsolutePath());
		reader.addListener(ToolFactory.makeWriter(fToConvert.getParent()+Parser.getInstance().getSlash()+newFileName, reader));
		while(reader.readPacket() == null);
		mdc.setFilename(newFileName);
		return fToConvert.getParent()+Parser.getInstance().getSlash()+newFileName;
	}
}
