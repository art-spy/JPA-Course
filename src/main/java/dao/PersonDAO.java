package dao;

import java.util.Collection;

import jakarta.persistence.*;

import model.Person;
import model.Sprache;

public class PersonDAO {

	private EntityManagerFactory factory;
	private EntityManager em;

	public PersonDAO() {
		factory = Persistence.createEntityManagerFactory("JPAKursprojekt");
		em = factory.createEntityManager();
	}

	public void shutdown() {
		em.close();
		factory.close();
		em = null;
		factory = null;
	}

	public Person find(long id) {
		return em.find(Person.class, id);
	}
	
	
	public Collection<Person> findAll() {
		TypedQuery<Person> query = em.createNamedQuery("findAll", Person.class);
		Collection<Person> collection = query.getResultList();
		return collection;
	}
	
	public Person findByVorname(String vorname) {
		Person p = (Person) em.createQuery("select p from Person p where p.vorname = :vn")
				.setParameter("vn", vorname)
				.getSingleResult();
		return p;
	}
	
	
	public void persist(Person p) {
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
	}
	

	public void delete(long id) {
		em.getTransaction().begin();
		Person p = em.getReference(Person.class, id); 
		em.remove(p);
		em.getTransaction().commit();
	}
	
	public void deleteAll() {
		em.getTransaction().begin();
		
		Query queryAdresse = em.createQuery("DELETE FROM Adresse a");
		int anzahlAdressen = queryAdresse.executeUpdate();

		Query queryEmailAdresse = em.createQuery("DELETE FROM Emailadresse e");
		int anzahlEmailAdressen = queryEmailAdresse.executeUpdate();
		
		Query queryPerson = em.createQuery("DELETE FROM Person p");
		int anzahlPersonen = queryPerson.executeUpdate();

		System.out.println(anzahlPersonen+ " Personen wurden gelöscht. "+anzahlAdressen+ " Adressen wurden gelöscht. "+ anzahlEmailAdressen+ " Emailadressen wurden gelöscht.");
		
		em.getTransaction().commit();
	}

	
	
	@SuppressWarnings("unchecked")
	public Collection<Sprache> findAllSprachen() {
		Query query = em.createQuery("SELECT s FROM Sprache s");
		Collection<Sprache> collection;
		collection = (Collection<Sprache>) query.getResultList();
		return collection;
	}

	

}
