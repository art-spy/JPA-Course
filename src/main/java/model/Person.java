package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity implementation class for Person entity
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {


    // primärer Schlüssel
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    private String vorname;
    private String nachname;

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    private Date geburtsdatum;

    private static final long serialVersionUID = 1L;

    public Person() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
}