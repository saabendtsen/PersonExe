package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Person;
import errorhandling.PersonNotFoundException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();


    public PersonFacade() {
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    @Override
    public PersonDTO addPerson(String fname, String Lname, String phone) {
        EntityManager em = getEntityManager();
        PersonDTO personDTO = new PersonDTO(fname, Lname, phone);
        Person person = new Person(personDTO);

        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }

    }

    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            Person person = em.find(Person.class, id);
            if (person == null){
                throw new PersonNotFoundException("Person not found");
            }
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }

    }

    @Override
    public PersonDTO getPerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            Person res = em.find(Person.class,id);
            if (res == null) {
                throw new PersonNotFoundException("Person not found");
            } else {
                return new PersonDTO(res);
            }
        } finally {
            em.close();
        }
    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> res = query.getResultList();
            System.out.println(res);
            return new PersonsDTO(res);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            if(em.find(Person.class,p.getId()) == null){
                throw new PersonNotFoundException("Person not found ");
            }else {
                Person person = em.merge(new Person(p));
                return new PersonDTO(person);
            }
        } finally {
            em.close();
        }
    }
}
