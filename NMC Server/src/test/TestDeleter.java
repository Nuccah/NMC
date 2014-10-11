package test;

import java.io.File;

import model.Config;

import org.junit.Test;

import controller.Deleter;

public class TestDeleter {

	@Test
	public void testDeleteFile() {
		Config.getInstance();
		Deleter del = Deleter.getInstance();
		del.getFilesToConvert().add(new File("C:\\Users\\Antoine\\NMC_STOCK\\Music\\Dear Boy.mp3"));
		del.getFilesToConvert().add(new File("C:\\Users\\Antoine\\NMC_STOCK\\Music\\Edom.mp3"));
		del.getFilesToConvert().add(new File("C:\\Users\\Antoine\\NMC_STOCK\\Music\\Heart Upon My Sleeve.mp3"));
		del.getFilesToConvert().add(new File("C:\\Users\\Antoine\\NMC_STOCK\\Music\\Hey Brother.mp3"));
		del.getFilesToConvert().add(new File("C:\\Users\\Antoine\\NMC_STOCK\\Music\\Hope There's Someone.mp3"));
		del.getFilesToConvert().add(new File("C:\\Users\\Antoine\\NMC_STOCK\\Music\\I Could Be The One.mp3"));
		del.deleteFile();
		
	}

}
