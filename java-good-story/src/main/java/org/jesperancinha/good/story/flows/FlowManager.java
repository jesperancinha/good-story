package org.jesperancinha.good.story.flows;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

import static java.lang.Thread.sleep;

/**
 * Created by jofisaes on 04/06/2022
 */
public class FlowManager {

    public String readWordFlowBack(List<String> words) {
        final var subscriber = new WordSubscriber();
        try (final var publisher = new SubmissionPublisher<String>()) {
            publisher.subscribe(subscriber);
            words.forEach(publisher::submit);
            final int wordsSize = words.size();
            do {
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (subscriber.getWords().size() < wordsSize);
        } catch (Exception ignored) {
        }
        return String.join(",", subscriber.getWords());
    }
}
