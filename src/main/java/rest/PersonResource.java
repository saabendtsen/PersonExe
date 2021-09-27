package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import dtos.PersonsDTO;
import errorhandling.PersonNotFoundException;
import facades.FacadeExample;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Path("person")
public class PersonResource {

    private final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        PersonsDTO list = FACADE.getAllPersons();
        return Response.ok(GSON.toJson(list),MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByID(@PathParam("id") int id) throws PersonNotFoundException {
        PersonDTO res = FACADE.getPerson(id);
        return GSON.toJson(res);
    }


    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPerson(String person){
        PersonDTO pDTO = GSON.fromJson(person,PersonDTO.class);
        PersonDTO pDTO1 = FACADE.addPerson(pDTO.getFirstName(), pDTO.getLastName(),pDTO.getPhone());
        return Response.ok(GSON.toJson(pDTO1),MediaType.APPLICATION_JSON_TYPE).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String editPerson( String id) throws PersonNotFoundException {
        PersonDTO pDTO = GSON.fromJson(id,PersonDTO.class);
        PersonDTO person = FACADE.editPerson(pDTO);
        return GSON.toJson(person);
    }

    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public String deletePerson(@PathParam("id") int id) throws PersonNotFoundException {
        PersonDTO person = FACADE.deletePerson(id);

        return GSON.toJson(person);
    }
}