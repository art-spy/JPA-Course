package model;

import jakarta.persistence.*;

@Entity
public class Emailadresse {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String emailadressestring;
	
	@ManyToOne
	private Person person;

	public String getEmailadressestring() {
		return emailadressestring;
	}

	public void setEmailadressestring(String emailadressestring) {
		this.emailadressestring = emailadressestring;
	}

	public void setPerson(Person person) {
		this.person = person;		
	}
	
	@Override
	public String toString() {
		return emailadressestring;
	}
}
