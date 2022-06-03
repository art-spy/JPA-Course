import dao.PersonDAO;
import model.Geschlecht;
import model.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.sql.Date;

public class Main {

    public static void main(String[] args) {

        insertMax();
        setBirthdate();
        printPersonDetails("Max");
        renameMaxToEva();
        printPersonDetails("Eva");
        //removePerson();

    }

    // im folgenden beispielhaft implementierte, einzelne Transaktionen (nur zur Anschauung)

    public static void insertMax(){
        PersonDAO personDAO = new PersonDAO();
        Person p = new Person();

        p.setVorname("Max");
        p.setNachname("Mustermann");
        p.setGeschlecht(Geschlecht.MAENNLICH);

        try {
            p.setPassbild(Files.readAllBytes(Paths.get("maxmustermann.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        p.setKommentar("Mein lieber Max Mustermann!");

        personDAO.persist(p);
        personDAO.shutdown();

    }

    public static void setBirthdate(){
        PersonDAO personDAO = new PersonDAO();
        Person p = personDAO.findByVorname("Max");

        Calendar cal = Calendar.getInstance();
        cal.set(1975, Calendar.MARCH, 15);
        Date geburtsdatum = new Date(cal.getTime().getTime());
        p.setGeburtsdatum(geburtsdatum);

        personDAO.persist(p);
        personDAO.shutdown();

    }

    public static void printPersonDetails(String vorname){
        PersonDAO personDAO = new PersonDAO();
        Person p = personDAO.findByVorname(vorname);
        // EntityNotFoundException, wenn es das Objekt in der Datenbank nicht gibt.

        if (p != null){
            System.out.println(p.getVorname() + " " + p.getNachname());
            System.out.println("Kommentar: " + p.getKommentar());
        }
        else {
            System.out.println("Die Person " +  vorname + " ist nicht vorhanden!");
        }

        personDAO.shutdown();
    }

    public static void renameMaxToEva(){
        PersonDAO personDAO = new PersonDAO();
        Person p = personDAO.findByVorname("Max");

        if (p != null){
            p.setVorname("Eva");
            p.setGeschlecht(Geschlecht.WEIBLICH);
            p.setKommentar("Meine liebe Eva Mustermann!");
        }
        else {
            System.out.println("Das Objekt ist nicht vorhanden!");
        }

        personDAO.persist(p);
        personDAO.shutdown();
    }

    public static void removePerson(){
        PersonDAO personDAO = new PersonDAO();
        Person p = personDAO.findByVorname("Eva");
        personDAO.delete(p.getId());

        // auf das Personen-Objekt kann in der objektorientierten Ebene weiterhin zugegriffen werden,
        // selbst wenn es bereits aus der Datenbank entfernt wurde:
        System.out.println("Gelöschte Person: " + p.getVorname() + " " + p.getNachname());

        //personDAO.persist(p);
        personDAO.shutdown();
    }




}
