package model;

import java.sql.Date;

/**
 * Classe comprenant toutes les infos de l'utilisateur connecté
 * @author Antoine
 * @version 1.0
 */
public class Profil {
	private static Profil instance = null;
	private String username;
	private String mail;
	private String first_name;
	private String last_name;
	private Date birthdate;
	private Date reg_date;
	
	/**
	 * Initialise toutes les variables du profil à null
	 */
	public void reset(){
		username = null;
		mail = null;
		first_name = null;
		last_name = null;
		birthdate = null;
		reg_date  = null;
	}
	
	protected Profil(){
		// Fake constructor for singleton
	}
	
	/**
	 * Permet de créer/récupérer l'instance Profil
	 * @return l'objet instancié du Profil
	 */
	public static Profil getInstance(){
		if(instance == null) instance = new Profil();
		return instance;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getFirstName() {
		return first_name;
	}
	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}
	public  String getLastName() {
		return last_name;
	}
	public void setLastName(String last_name) {
		this.last_name = last_name;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public Date getRegDate() {
		return reg_date;
	}
	public void setRegDate(Date reg_date) {
		this.reg_date = reg_date;
	}

}
