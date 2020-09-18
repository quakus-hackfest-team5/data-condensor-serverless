package quarkus.hackfest.messaging;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.infinispan.client.hotrod.RemoteCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.infinispan.client.Remote;
import quarkus.hackfest.datamodel.Guess;
import quarkus.hackfest.datamodel.Statistics;

@ApplicationScoped
public class GuessSubscriber {
    private static Logger log = LoggerFactory.getLogger(GuessSubscriber.class);
    
    @Inject 
    @Remote("statistics")
    RemoteCache<String, Statistics> stats;

    @Incoming("twitter-replies-out")
    public void processGuess(Guess guess) {
        log.info("Guess:  GP={}, Driver={}", guess.getGp(), guess.getDriver());

        Statistics existing = stats.get(guess.getGp());

        //No existing statistics was generated
        if (existing == null) {
            existing = new Statistics();
            existing.setGp(guess.getGp());
        }

        //Increment current sum
        existing.incrementDriver(guess.getDriver());

        //Update the cache
        stats.put(guess.getGp(), existing);
    }
}
