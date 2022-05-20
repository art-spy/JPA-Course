package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import model.Person;

import java.util.Collection;

/**
 * Data Access Object (DAO) pattern for Person object
 */
public class PersonDAO {

    private EntityManagerFactory factory;
    private EntityManager em;

    public PersonDAO() {
        factory = Persistence.createEntityManagerFactory("JPAKursprojekt");
        em = factory.createEntityManager();
    }

    public void shutdown(){
        em.close();
        factory.close();
        em = null;
        factory = null;
    }

    public Person find (long id){
        return em.find(Person.class, id);
    }

    @SuppressWarnings("unchecked")
    public Collection<Person> findAll(){
        // warum select p anstatt select * ?
        Query query = em.createQuery("select p from Person p");
        Collection<Person> collection = (Collection<Person>) query.getResultList();
        return collection;
    }

    public Person findByVorname(String vorname){
        Person p = (Person) em.createQuery("select p from Person p where p.vorname = :vn")
                .setParameter("vn", vorname)
                .getResultList()
                .get(0);
        return p;
    }

    // persist = insert/update Person p into database
    public void persist(Person p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public void delete(long id){
        em.getTransaction().begin();
        Person p = em.getReference(Person.class, id);
        // EntityNotFoundException, wenn es das Objekt in der Datenbank nicht gibt.
        em.remove(p);

        // auf das Personen-Objekt kann in der objektorientierten Ebene weiterhin zugegriffen werden,
        // selbst wenn es bereits aus der Datenbank entfernt wurde:
        // System.out.println("Gelöschte Person: " + p.getVorname() + " " + p.getNachname());

        em.getTransaction().commit();
    }

}
