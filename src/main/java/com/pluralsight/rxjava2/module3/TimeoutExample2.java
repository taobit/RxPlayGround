package com.pluralsight.rxjava2.module3;

import com.pluralsight.rxjava2.utility.GateBasedSynchronization;
import com.pluralsight.rxjava2.utility.ThreadKt;
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet;
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class TimeoutExample2 {

   private final static Logger log = LoggerFactory.getLogger(TimeoutExample2.class);

    public static void main(String[] args) {

        GateBasedSynchronization gate = new GateBasedSynchronization();

        // Create a custom Observable that will emit alpha, beta, pause for a day, and
        // then emit gamma.
        Observable<Object> greekAlphabetWithBigDelay = Observable.create(emitter -> {
            ThreadKt.sleep(1,TimeUnit.DAYS);            // wait 1 days
            Arrays.stream(GreekAlphabet.getGreekLettersInEnglish())
                    .forEach(emitter::onNext);
            emitter.onComplete();
        })

        // timeout emits the "timeout" onError on the computation thread pool
        // so we make the entire subscription happen on the same thread pool.
        // This is because the main thread may be the thread that is
        // hung and causes the timeout.
        .subscribeOn(Schedulers.computation())
        .timeout(2, TimeUnit.SECONDS
                , GreekAlphabet.greekAlphabetInGreekObservable());

        greekAlphabetWithBigDelay.subscribe(new DemoSubscriber<>(gate));

        gate.waitForAny("onComplete", "onError");

        System.exit(0);
    }
}
