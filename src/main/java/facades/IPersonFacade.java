package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import errorhandling.PersonNotFoundException;

import java.util.List;

public interface IPersonFacade {
    public PersonDTO addPerson(String fname, String Lname, String phone);
    public PersonDTO deletePerson(int id) throws PersonNotFoundException;
    public PersonDTO getPerson(int id) throws PersonNotFoundException;
    public PersonsDTO getAllPersons() ;
    public PersonDTO editPerson(PersonDTO p)throws PersonNotFoundException;
}


