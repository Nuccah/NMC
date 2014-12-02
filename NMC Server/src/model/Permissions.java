/**
 * 
 */
package model;

import java.io.Serializable;

/**
 * Classe contenant les données des permissions
 * @author Derek
 *
 */
public class Permissions implements Serializable{
	private static final long serialVersionUID = -828799344015351136L;
	private int id;
	private String label;
	private int level;
	
	public Permissions(String label, int level) {
		super();
		this.label = label;
		this.level = level;
	}
	
	public Permissions(int id, String label, int level) {
		super();
		this.id = id;
		this.label = label;
		this.level = level;
	}
	/**
	 * Permet d'obtenir l'identifiant du rang
	 * @return L'id du rang
	 */
	public int getId() {
		return id;
	}
	/**
	 * Permet de modifier l'identifiant du rang
	 * @param id : Nouvel identifiant du rang
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Permet d'obtenir le nom du rang
	 * @return Le nom du rang
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * Permet de modifier le nom du rang
	 * @param label : Le nouveau nom du rang
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * Permet d'obtenir le niveau du rang
	 * @return Le niveau du rang
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * Permet de modifier le niveau du rang
	 * @param level : Le nouveau niveau du rang
	 */
	public void setLevel(int level) {
		this.level = level;
	}
}
