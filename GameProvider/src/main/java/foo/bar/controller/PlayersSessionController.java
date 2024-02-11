package foo.bar.controller;

import io.quarkus.oidc.Tenant;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;


@Path("/validate-players")
@RequestScoped
public class PlayersSessionController {

    private static final Logger LOG = LoggerFactory.getLogger(PlayersSessionController.class);
    @Inject
    JsonWebToken jwt;

    @Inject
    SecurityContext securityContext;

    @POST
    @Tenant("players")
    public Response processPlayerRequest() throws Exception {
        Object upn = jwt.getClaim("upn");
        if (securityContext.getUserPrincipal() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid player token").build();
        }
        LOG.debug("Player " + upn + " was successfully authenticated");
        //todo get info from token and perform domain logic
        return Response.ok("Game start/proceeds: Welcome " + upn).build();
    }
}
