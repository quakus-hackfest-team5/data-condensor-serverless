package quarkus.hackfest.data;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;
import quarkus.hackfest.datamodel.Guess;

public class GuessDeserializer extends JsonbDeserializer<Guess> {

    public GuessDeserializer() {
        super(Guess.class);
    }
    
}
