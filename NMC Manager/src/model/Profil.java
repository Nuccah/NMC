package model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Classe comprenant toutes les infos de l'utilisateur connecté
 * @author Antoine
 * @version 1.0
 */
public class Profil implements Serializable {
	private static final long serialVersionUID = 1399111452938397134L;
	private static Profil instance = null;
	private int id;
	private String username;
	private String password;
	private String mail;
	private String first_name;
	private String last_name;
	private Date birthdate;
	private Timestamp reg_date;
	private int permissions_id;
	
	/**
	 * Initialise toutes les variables du profil à null
	 */
	public void reset(){
		id = 0;
		username = null;
		password = null;
		mail = null;
		first_name = null;
		last_name = null;
		birthdate = null;
		reg_date  = null;
		permissions_id = 0;
	}
	
	public Profil(int users_id, String login, String email, String first_name, String last_name, Date birthdate, Timestamp reg_date, int permissions_id){
		this.id = users_id;
		this.username = login;
		this.password = null;
		this.mail = email;
		this.first_name = first_name;
		this.last_name = last_name;
		this.birthdate = birthdate;
		this.reg_date  = reg_date;
		this.permissions_id = permissions_id;
	}
	
	public Profil(int users_id, String login, String password, String email, String first_name, String last_name, Date birthdate, Timestamp reg_date, int permissions_id){
		this.id = users_id;
		this.username = login;
		this.password = password;
		this.mail = email;
		this.first_name = first_name;
		this.last_name = last_name;
		this.birthdate = birthdate;
		this.reg_date  = reg_date;
		this.permissions_id = permissions_id;
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
	/**
	 * Permet de créer une instance statique profil à partir de celle passée en paramètre
	 * @param pf : Profil distant à copier localement
	 */
	public static void setInstance(Profil pf){
		instance = pf;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	public Timestamp getRegDate() {
		return reg_date;
	}
	public void setRegDate(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	public int getPermissions_id() {
		return permissions_id;
	}

	public void setPermissions_id(int permissions_id) {
		this.permissions_id = permissions_id;
	}
}
