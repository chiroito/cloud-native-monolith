package org.example.jakartasample.parameter;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/mp/parameter")
public class ParameterisedMpResource {

    @Inject
    @ConfigProperty(name="CONFIG_1", defaultValue = "デフォルト値")
    String config1;

    /**
     * MicroProfile Configから情報を取得するサンプル
     * @return
     */
    @GET
    @Produces("text/plain")
    public String mpConfig() {
        return config1;
    }
}
