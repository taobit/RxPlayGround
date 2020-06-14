package com.pluralsight.rxjava2.module5;

import com.pluralsight.rxjava2.utility.GateBasedSynchronization;
import com.pluralsight.rxjava2.utility.ThreadKt;
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class AsyncSubjectExample1 {

    private final static Logger log = LoggerFactory.getLogger(AsyncSubjectExample1.class);

    public static void main(String[] args) {

        GateBasedSynchronization gate = new GateBasedSynchronization();

        // Create an AsyncSubject that will contain the last event streamed
        // to it before it is closed.
        AsyncSubject<String> asyncFetchSubject = AsyncSubject.create();

        // Perform a long running operation that will emit
        // an event into our AsyncSubject
        Runnable longRunningAction = () -> {

            // Sleep for 2 seconds...
            ThreadKt.sleep(2, TimeUnit.SECONDS);

            // Emit some data
            asyncFetchSubject.onNext("Hello World 1");
            asyncFetchSubject.onNext("Hello World 2");
            asyncFetchSubject.onNext("Hello World 3");

            // Sleep some more
            ThreadKt.sleep(1, TimeUnit.SECONDS);

            // Complete the stream
            asyncFetchSubject.onComplete();

            // Open the synchonization gate called "onComplete"
            gate.openGate("onComplete");
        };

        // Create a subscriber to the AsyncSubject
        asyncFetchSubject
                .subscribeOn(Schedulers.computation())
                .subscribe(new DemoSubscriber<>());

        // Execute the long running action on the IO scheduler
        Schedulers.io().scheduleDirect(longRunningAction);

        gate.waitForAny("onComplete");

        System.exit(0);

    }
}
