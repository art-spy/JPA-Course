package model;

import jakarta.persistence.*;

@Entity
public class Geschaeftspartner extends Person {
	private static final long serialVersionUID = 1567265267681615011L;
	
	private String position;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	} 
	
}
