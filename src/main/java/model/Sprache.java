package model;

import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Sprache {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToMany (mappedBy="sprachen")
	private Set<Person> personen;
	
	
	private String name;

	public Sprache() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name; 
	}
	

}
