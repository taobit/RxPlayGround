package com.pluralsight.rxjava2.module3;

import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static com.pluralsight.rxjava2.utility.ThreadKt.sleep;

public class SampleExample1 {

    public static void main(String[] args) {

        // Create a repeating greek alphabet
        Observable<Long> incrementingObservable = Observable.interval(0L, 50L, TimeUnit.MILLISECONDS)

                // Like timeout, sample must use a different thread pool
                // so that it can send a message event though events
                // may be being generated on the main thread.
                .subscribeOn(Schedulers.computation())

                // Sample the stream every 2 seconds.
                .sample(100, TimeUnit.MILLISECONDS)
                ;

        // Subscribe and watch the emit happen every 2 seconds.
        incrementingObservable.subscribe(new DemoSubscriber<>());

        // Wait for 10 seconds
        sleep(10, TimeUnit.SECONDS);

        System.exit(0);
    }
}
