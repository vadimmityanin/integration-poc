package org.example.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;

import io.quarkus.oidc.client.OidcClient;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Path("/tokens")
public class GamePlatformController {

    @Inject
    OidcClient client;


    @GET
    public String getToken(@QueryParam("scope") String scope) {
        // Specify the desired scope in a Map
        Map<String, String> additionalGrantParameters = new HashMap<>();
        additionalGrantParameters.put("scope", scope);

        return client.getTokens(additionalGrantParameters).await().atMost(Duration.ofSeconds(1)).getAccessToken();
    }

}
