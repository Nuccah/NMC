package model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Classe comprenant toutes les infos de l'utilisateur connecté
 * @author Antoine
 * @version 1.0
 */
public class Profil implements Serializable {
	private static final long serialVersionUID = 1245938594141489262L;
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
	
	public Profil(String login, String password, String email, String first_name, String last_name, Date birthdate, int permissions_id){
		this.username = login;
		this.password = password;
		this.mail = email;
		this.first_name = first_name;
		this.last_name = last_name;
		this.birthdate = birthdate;
		this.permissions_id = permissions_id;
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
	/**
	 * Permet d'obtenir l'identifiant du profil
	 * @return L'identifiant du profil
	 */
	public int getId() {
		return id;
	}
	/**
	 * Permet de modifier l'identifiant du profil
	 * @param id : Le nouvel identifiant du profil
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Permet d'obtenir le nom d'utilisateur
	 * @return Le nom d'utilisateur
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Permet de modifier le nom d'utilisateur
	 * @param username : Le nouveau nom d'utilisateur
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * Permet d'obtenir le mot de passe 
	 * @return Le mot de passe
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Permet modifier le mot de passe
	 * @param password : Le nouveau mot de passe
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Permet d'obtenir l'adresse mail
	 * @return l'adresse mail
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * Permet de modifier l'adresse mail
	 * @param mail : La nouvelle adresse mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * Permet d'obtenir le prénom
	 * @return Le prénom
	 */
	public String getFirstName() {
		return first_name;
	}
	/**
	 * Permet de modifier le prénom
	 * @param first_name : Le nouveau prénom
	 */
	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}
	/**
	 * Permet d'obtenir le nom
	 * @return Le nom
	 */
	public  String getLastName() {
		return last_name;
	}
	/**
	 * Permet de modifier le nom
	 * @param last_name : Le nouveau nom
	 */
	public void setLastName(String last_name) {
		this.last_name = last_name;
	}
	/**
	 * Permet d'obtenir la date de naissance
	 * @return La date de naissance
	 */
	public String getBirthdate() {
		return new SimpleDateFormat("dd/MM/yyyy").format(birthdate);
	}
	/**
	 * Permet de modifier la date de naissance
	 * @param birthdate : La nouvelle date de naissance
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	/**
	 * Permet d'obtenir la date d'enregistrement
	 * @return La date d'enregistrement
	 */
	public String getRegDate() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(reg_date);
	}
	/**
	 * Permet de modifier la date d'enregistrement
	 * @param reg_date : La nouvelle date d'enregistrement
	 */
	public void setRegDate(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	/**
	 * Permet d'obtenir le rang
	 * @return L'identifiant du rang
	 */
	public int getPermissions_id() {
		return permissions_id;
	}
	/**
	 * Permet de modifier le rang
	 * @param permissions_id : L'identifiant du nouveau rang
	 */
	public void setPermissions_id(int permissions_id) {
		this.permissions_id = permissions_id;
	}
}
