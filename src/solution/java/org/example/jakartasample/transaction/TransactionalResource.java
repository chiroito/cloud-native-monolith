package org.example.jakartasample.transaction;

import org.example.jakartasample.transaction.jpa.CountData;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/jee/transaction")
public class TransactionalResource {

    @PersistenceContext
    private EntityManager em;

    @GET
    @Path("/success")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public String success() {
        invoke();
        return "Success";
    }

    @GET
    @Path("/fail")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public String fail(){
        invoke();
        throw new RuntimeException("Transaction fail");
    }

    @GET
    @Path("view")
    @Produces(MediaType.APPLICATION_JSON)
    public String view(){
        CountData data = em.find(CountData.class, "key");
        return "Count is " + data.getCount();
    }

    private void invoke(){
        CountData data = em.find(CountData.class, "key");
        data.setCount(data.getCount() + 1);
        em.merge(data);
    }

}
