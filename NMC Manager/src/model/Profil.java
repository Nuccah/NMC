package model;

import java.sql.Date;

/**
 * Classe comprenant toutes les infos de l'utilisateur connecté
 * @author Antoine
 *
 */
public class Profil {
	private static String username;
	private static String mail;
	private static String first_name;
	private static String last_name;
	private static Date birthdate;
	private static Date reg_date;
	/**
	 * Initialise toutes les variables du profil à null
	 */
	public static void init(){
		Profil.username = null;
		Profil.mail = null;
		Profil.first_name = null;
		Profil.last_name = null;
		Profil.birthdate = null;
		Profil.reg_date  = null;
	}
	
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		Profil.username = username;
	}
	public static String getMail() {
		return mail;
	}
	public static void setMail(String mail) {
		Profil.mail = mail;
	}
	public static String getFirstName() {
		return first_name;
	}
	public static void setFirstName(String first_name) {
		Profil.first_name = first_name;
	}
	public static String getLastName() {
		return last_name;
	}
	public static void setLastName(String last_name) {
		Profil.last_name = last_name;
	}
	public static Date getBirthdate() {
		return birthdate;
	}
	public static void setBirthdate(Date birthdate) {
		Profil.birthdate = birthdate;
	}
	public static Date getRegDate() {
		return reg_date;
	}
	public static void setRegDate(Date reg_date) {
		Profil.reg_date = reg_date;
	}
	
	

}
