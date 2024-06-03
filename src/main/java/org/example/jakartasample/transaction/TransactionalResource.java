package org.example.jakartasample.transaction;

import org.example.jakartasample.transaction.jpa.RdbmsData;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
        RdbmsData data = em.find(RdbmsData.class, "key");
        return "Count is " + data.getCount();
    }

    private void invoke(){
        RdbmsData data = em.find(RdbmsData.class, "key");
        data.setCount(data.getCount() + 1);
        em.merge(data);
    }

}
