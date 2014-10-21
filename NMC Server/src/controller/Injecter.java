/**
 * 
 */
package controller;

import model.AlbumCollector;
import model.AudioCollector;
import model.BookCollector;
import model.EpisodeCollector;
import model.ImageCollector;
import model.SeriesCollector;
import model.VideoCollector;
import model.MetaDataCollector.*;

/**
 * Class permitting to inject metadata into database
 * @author Derek
 *
 */
public class Injecter {
	private static Injecter instance = null;

	protected Injecter(){
		// Fake constructor for singleton
	}

	public static Injecter getInstance(){
		if(instance == null) instance = new Injecter();
		return instance;
	}
	
	public void injector(AudioCollector audio){
		
	}
	
	public void injector(AlbumCollector album){
		
	}
	
	public void injector(BookCollector book){
		
	}
	
	public void injector(ImageCollector image){
		
	}
	
	public void injector(VideoCollector video){
		
	}
	
	public void injector(SeriesCollector series){
		
	}
	
	public void injector(EpisodeCollector episode){
		
	}
}
