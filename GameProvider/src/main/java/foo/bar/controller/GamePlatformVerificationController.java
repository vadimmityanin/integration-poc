package foo.bar.controller;

import foo.bar.model.GamePlatformIntrospectionRequest;
import foo.bar.model.GamePlatformIntrospectionResponse;
import io.quarkus.oidc.Tenant;
import io.smallrye.jwt.build.Jwt;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.Claim;

import java.time.Instant;


@Path("/validate-platform-tokens")
@RequestScoped
public class GamePlatformVerificationController {

    private static final Logger LOG = LoggerFactory.getLogger(GamePlatformVerificationController.class);

    public static final String SUPPORTED_GAMES = "supergame1";
    @Inject
    @Claim("scope")
    String scope;

    @Inject
    SecurityContext securityContext;


    @POST
    @Tenant("gameplatform")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response introspect(GamePlatformIntrospectionRequest request) {
        // todo if token is active only in that case scope will be populated
        // todo remove hardcode
        if (securityContext.getUserPrincipal() != null && scope != null && !scope.contains("\"" + SUPPORTED_GAMES + "\"")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unsupported game/Invalid token").build();
        }

        Instant now = Instant.now();
        String playerSessionToken = Jwt.issuer("gameProvider")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(30))
                .upn(request.getUserId())
                .sign();

        //todo add refresh tokens support
        return Response.ok(GamePlatformIntrospectionResponse.of(playerSessionToken), MediaType.APPLICATION_JSON).build();
    }

}
