package model;

import java.io.Serializable;
import java.lang.String;
import java.sql.Date;

import jakarta.persistence.*;

import model.listener.PersonListener;

/**
 * Entity implementation class for Entity: Person
 *
 */
@Entity
@EntityListeners(PersonListener.class)
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen")
	@SequenceGenerator(name="gen", sequenceName="Personensequenz", initialValue=1000, allocationSize=10)
	private long id;
	private String vorname;
	private String nachnamen;
	
	
	private Date geburtsdatum;
	
	@Enumerated(EnumType.STRING) 
	private Geschlecht geschlecht;
	
	@Lob
	private byte[] passbild;
	
	@Column(name="HINWEIS")
	private String kommentar;
		
	public Date getGeburtsdatum() {
		return geburtsdatum;
	}
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	private static final long serialVersionUID = 1L;

	public Person() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}   
	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}   
	public String getNachnamen() {
		return this.nachnamen;
	}

	public void setNachnamen(String nachnamen) {
		this.nachnamen = nachnamen;
	}
	public Geschlecht getGeschlecht() {
		return geschlecht;
	}
	public void setGeschlecht(Geschlecht geschlecht) {
		this.geschlecht = geschlecht;
	}
	public byte[] getPassbild() {
		return passbild;
	}
	public void setPassbild(byte[] passbild) {
		this.passbild = passbild;
	}
	public String getKommentar() {
		return kommentar;
	}
	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}
	
	@Override
	public String toString() {
		
		return vorname + " " + nachnamen;
	}
	
}
