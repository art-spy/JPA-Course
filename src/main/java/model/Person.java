package model;
 
import java.io.Serializable;
import java.lang.String;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.listener.PersonListener;

/**
 * Entity implementation class for Entity: Person
 *
 */
@Entity
@EntityListeners(PersonListener.class)
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="Persontyp")
public abstract class Person implements Serializable {

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

	@OneToOne (mappedBy="person", cascade=CascadeType.PERSIST)
	private Adresse adresse;
	
	@OneToMany(mappedBy="person", cascade=CascadeType.PERSIST, orphanRemoval=true)
	private Set<Emailadresse> emailaddresses;	
	
	@ManyToMany (cascade=CascadeType.PERSIST)
	private Set<Sprache> sprachen;
	
			
	public Date getGeburtsdatum() {
		return geburtsdatum;
	}
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	private static final long serialVersionUID = 1L;

	public Person() {
		super();
		emailaddresses = new HashSet<>();
		sprachen = new HashSet<>();
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
	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		
		if (this.adresse != null) 
			this.adresse.setPerson(null);
		
		this.adresse = adresse;
	}
	
	public void addEmailadresse(String emailadressstring) {
		Emailadresse emailadresse = new Emailadresse();
		emailadresse.setEmailadressestring(emailadressstring);
		emailadresse.setPerson(this);
		emailaddresses.add(emailadresse);		
	}
	
	public void removeEmailadresse(Emailadresse emailadresse) {
		emailadresse.setPerson(null);
		emailaddresses.remove(emailadresse);
	}
	public ObservableList<Emailadresse> getEmailadresses() {
		return FXCollections.observableArrayList(emailaddresses);
	}
	
	public Set<Sprache> getSprachen() {
		return sprachen;
	}
	
	public void clearSprachen() {
		sprachen.clear();
	}
	
	public void addSprache (Sprache sprache)
	{
		this.sprachen.add(sprache);
	}
	
	
}








