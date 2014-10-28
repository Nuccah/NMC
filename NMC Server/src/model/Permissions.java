/**
 * 
 */
package model;

import java.io.Serializable;

/**
 * @author Derek
 *
 */
public class Permissions implements Serializable{
	private static final long serialVersionUID = -828799344015351136L;
	private int id;
	private String label;
	private int level;
	

	public Permissions(int id, String label, int level) {
		super();
		this.id = id;
		this.label = label;
		this.level = level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
