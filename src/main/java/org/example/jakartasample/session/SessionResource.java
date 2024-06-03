package org.example.jakartasample.session;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/jee/session")
public class SessionResource {

    @Inject
    SessionBean sessionBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String invoke(){
        int count = sessionBean.incrementCount();
        return "{\"count\":" + count + "}";
    }
}
