package com.pluralsight.rxjava2.module3;

import com.pluralsight.rxjava2.utility.GateBasedSynchronization;
import com.pluralsight.rxjava2.utility.ThreadKt;
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet;
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class TimeoutExample {

    public static void main(String[] args) {

        GateBasedSynchronization gate = new GateBasedSynchronization();

        // Create a custom Observable that will emit alpha, beta, pause for a day, and
        // then emit gamma.
        Observable<Object> greekAlphabetWithBigDelay = Observable.create(emitter -> {
            emitter.onNext(GreekAlphabet.getGreekLettersInEnglish()[0]);  // Emit alpha
            emitter.onNext(GreekAlphabet.getGreekLettersInEnglish()[1]);  // Emit beta
            ThreadKt.sleep(1,TimeUnit.DAYS);            // wait 1 days
            emitter.onNext(GreekAlphabet.getGreekLettersInEnglish()[2]);  // Emit gamma
            emitter.onComplete();
        })

        // timeout emits the "timeout" onError on the computation thread pool
        // so we make the entire subscription happen on the same thread pool.
        // This is because the main thread may be the thread that is
        // hung and causes the timeout.
        .subscribeOn(Schedulers.computation())
        .timeout(2, TimeUnit.SECONDS);

        greekAlphabetWithBigDelay.subscribe(new DemoSubscriber<>(gate));

        gate.waitForAny("onComplete", "onError");

        System.exit(0);
    }
}
