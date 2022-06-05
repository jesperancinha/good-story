package org.jesperancinha.good.story.flows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

/**
 * Created by jofisaes on 04/06/2022
 */
class WordSubscriber implements Flow.Subscriber<String> {

    private final List<String> words = new ArrayList<>();
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(String word) {
        words.add(word);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onComplete() {
    }

    public List<String> getWords() {
        return words;
    }
}