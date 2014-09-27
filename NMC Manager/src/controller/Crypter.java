package controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Classe de cryptage et décryptage de mots de passe
 * @author Antoine
 *
 */
public class Crypter {
	/**
	 * Permet de crypter les données entrées (data) via l'algorithme SHA-512
	 * @param data : Données à crypter
	 * @return String contenant les données cryptées au format Hexadécimal
	 */
	public static String encrypt(String data){
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512", "BC");
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			System.out.println("Algorithme de cryptage ou fournisseur non trouvé!");
			e.printStackTrace();
		}
		
		byte[] digest = md.digest(data.getBytes());
		return String.format("%0128x", new BigInteger(1, digest));
	}
	/**
	 * Permet de vérifier si les données entrées sont égales
	 * @param data1 : Premières données à vérifier 
	 * @param data2 : Secondes données à vérifier
	 * @return Vrai si les données sont égales
	 */
	public static boolean verify(String data1, String data2){
		return (data1.compareTo(data2) == 0);
	}
}
