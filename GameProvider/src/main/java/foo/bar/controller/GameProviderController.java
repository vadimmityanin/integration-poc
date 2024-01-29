package foo.bar.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;


@Path("/validate-platofrm-tokens")
@RequestScoped
public class GameProviderController {

    public static final String SUPPORTED_GAMES = "supergame1";
    @Inject
    @Claim("scope")
    String scope;

    //1 todo add own auth here
    @POST
    public Response introspect() {
        //2 todo if token is active only in that case scope will bu populated
        //3 todo remove hardcode
        if (!scope.contains("\"" + SUPPORTED_GAMES + "\"")){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unsupported game/Invalid token").build();
        }
        //4 todo add generation of session token for internal game-status tracking functionaloty
        // todo it is pretty much the same as for GamePlatform's service token obtaining
        return Response.ok().build();
    }
}
