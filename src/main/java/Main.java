import dao.PersonDAO;
import model.Person;

import java.util.Calendar;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        insertMax();
        setBirthdate();
        renameMaxToEva();
        printName();
        //removePerson();

    }

    // im folgenden beispielhaft implementierte, einzelne Transaktionen (nur zur Anschauung)

    public static void insertMax(){
        PersonDAO personDAO = new PersonDAO();
        Person p = new Person();

        p.setVorname("Max");
        p.setNachname("Mustermann");

        personDAO.persist(p);
        personDAO.shutdown();

    }

    public static void printName(){
        PersonDAO personDAO = new PersonDAO();
        Person p = personDAO.findByVorname("Eva");
        // EntityNotFoundException, wenn es das Objekt in der Datenbank nicht gibt.

        if (p != null){
            System.out.print(p.getVorname() + " ");
            System.out.println(p.getNachname());
        }
        else {
            System.out.println("Das Objekt ist nicht vorhanden!");
        }

        personDAO.shutdown();
    }

    public static void renameMaxToEva(){
        PersonDAO personDAO = new PersonDAO();
        Person p = personDAO.findByVorname("Max");

        if (p != null){
            p.setVorname("Eva");
        }
        else {
            System.out.println("Das Objekt ist nicht vorhanden!");
        }

        personDAO.persist(p);
        personDAO.shutdown();
    }

    public static void setBirthdate(){
        PersonDAO personDAO = new PersonDAO();
        Person p = personDAO.findByVorname("Max");

        Calendar cal = Calendar.getInstance();
        cal.set(1975, Calendar.MARCH, 15);
        Date geburtsdatum = cal.getTime();

        p.setGeburtsdatum(geburtsdatum);

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
