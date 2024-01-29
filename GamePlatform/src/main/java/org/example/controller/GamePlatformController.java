package org.example.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;

import io.quarkus.oidc.client.OidcClient;
import jakarta.ws.rs.Path;

import java.time.Duration;


@Path("/tokens")
public class GamePlatformController {

    @Inject
    OidcClient client;


    @GET
    public String getToken() {
        return client.getTokens().await().atMost(Duration.ofSeconds(1)).getAccessToken();
    }

}
