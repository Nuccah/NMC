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
	private String label;
	private int level;
	

	public Permissions(String label, int level) {
		super();
		this.label = label;
		this.level = level;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.label;
	}
	
}
