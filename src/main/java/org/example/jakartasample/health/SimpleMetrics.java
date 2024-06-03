package org.example.jakartasample.health;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.metrics.annotation.Counted;

import java.time.LocalDateTime;

@Path("/m")
public class SimpleMetrics {

    @GET
    @Produces("text/plain")
    @Counted(name = "requestCount",
            absolute = true,
            description = "Number of times the hello was requested")
    public String hello() {
        return LocalDateTime.now().toString();
    }
}
