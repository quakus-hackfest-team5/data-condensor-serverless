package quarkus.hackfest.resource;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import quarkus.hackfest.datamodel.Guess;

@Path("/gen-test")
public class InsertTestResource {

    @Inject
    @Channel("twitter-replies-in")
    Emitter<Guess> gpEmmiter;

    @POST
    public Response insertTestMessages() {
        try {
            List<Guess> gpGen = Arrays.asList(new Guess("Malasia", "Lewis Hamilton"),
                    new Guess("Malasia", "Valtteri Bottas"));

            gpGen.stream().forEach(g -> gpEmmiter.send(g));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
        return Response.accepted().build();
    }
}
