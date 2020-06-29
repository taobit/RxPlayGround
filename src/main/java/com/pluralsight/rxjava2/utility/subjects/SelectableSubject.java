package com.pluralsight.rxjava2.utility.subjects;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.util.HashMap;

public class SelectableSubject<TEventType> {

    private Subject<TEventType> internalSubject;
    private HashMap<Observable<TEventType>, Disposable> producerTrackingMap;
    private HashMap<Observer<TEventType>, Disposable> consumerTrackingMap;

    public SelectableSubject() {
        this.producerTrackingMap = new HashMap<>();
        this.consumerTrackingMap = new HashMap<>();
        this.internalSubject = PublishSubject.create();
    }

    public SelectableSubject(Subject<TEventType> subjectToUse) {
        this.producerTrackingMap = new HashMap<>();
        this.consumerTrackingMap = new HashMap<>();
        this.internalSubject = subjectToUse;
    }

    public synchronized void addEventProducer(Observable<TEventType> newEventSource) {

        // Have the internalSubject subscribe to the incoming event source.
        newEventSource
                .doOnSubscribe(
                        disposable -> {
                            // Intercept the onSubscribe and track the associated disposable.
                            this.producerTrackingMap.put(newEventSource, disposable);

                            // Pass the event along to the Subject
                            this.internalSubject.onSubscribe(disposable);
                        }
                )
                .subscribe(
                        internalSubject::onNext,
                        internalSubject::onError,
                        internalSubject::onComplete
                );
    }

    public synchronized void removeEventProducer(Observable<TEventType> eventSourceToRemove) {

        if (producerTrackingMap.containsKey(eventSourceToRemove)) {

            // Remove the tracking reference and call dispose to stop
            // the flow of messages to the subject.
            producerTrackingMap.remove(eventSourceToRemove).dispose();
        }
    }

    public synchronized void addEventConsumer(Observer<TEventType> newConsumer) {

        internalSubject.doOnSubscribe(disposable -> {

            // Intercept the disposable for this subscription
            consumerTrackingMap.put(newConsumer, disposable);

            // Pass the onSubscribe along to the Observer.
            newConsumer.onSubscribe(disposable);
        }).subscribe(
                newConsumer::onNext,
                newConsumer::onError,
                newConsumer::onComplete
        );
    }

    public synchronized void detachEventConsumer(Observer<TEventType> consumerToRemove) {

        if (consumerTrackingMap.containsKey(consumerToRemove)) {

            // Remove the reference from the tracking map and then
            // call dispose to stop the flow of events.
            consumerTrackingMap.remove(consumerToRemove).dispose();
        }
    }
}
