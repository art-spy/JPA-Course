import dao.PersonDAO;
import model.Person;

public class Main {

    public static void main(String[] args) {

        insertPerson();
        updatePerson();
        selectPerson();
        removePerson();

    }

    // im folgenden beispielhaft implementierte, einzelne Transaktionen (nur zur Anschauung)

    public static void insertPerson(){
        PersonDAO personDAO = new PersonDAO();
        Person p = new Person();

        p.setVorname("Max");
        p.setNachname("Mustermann");

        personDAO.persist(p);
        personDAO.shutdown();

    }

    public static void selectPerson(){
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

    public static void updatePerson(){
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
