package com.pluralsight.rxjava2.utility.subjects

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

open class SelectableSubject<TEventType>(private var internalSubject: Subject<TEventType> = PublishSubject.create()) {

    private var producerTrackingMap = mutableMapOf<Observable<TEventType>, Disposable>()
    private var consumerTrackingMap = mutableMapOf<Observer<TEventType>, Disposable>()

    @Synchronized
    fun addEventProducer(newEventSource: Observable<TEventType>) {
        // Have the internalSubject subscribe to the incoming event source.
        newEventSource.doOnSubscribe {
            // Intercept the onSubscribe and track the associated disposable.
            producerTrackingMap[newEventSource] = it

            // Pass the event along to the Subject
            internalSubject.onSubscribe(it)
        }.subscribe(
                { internalSubject.onNext(it) },
                { internalSubject.onError(it) })
        { internalSubject.onComplete() }
    }

    @Synchronized
    fun removeEventProducer(eventSourceToRemove: Observable<TEventType>?) {
        if (producerTrackingMap.containsKey(eventSourceToRemove)) {

            // Remove the tracking reference and call dispose to stop
            // the flow of messages to the subject.
            producerTrackingMap.remove(eventSourceToRemove)!!.dispose()
        }
    }

    @Synchronized
    fun addEventConsumer(newConsumer: Observer<TEventType>) {
        internalSubject.doOnSubscribe {

            // Intercept the disposable for this subscription
            consumerTrackingMap[newConsumer] = it

            // Pass the onSubscribe along to the Observer.
            newConsumer.onSubscribe(it)
        }.subscribe({ newConsumer.onNext(it) }, { newConsumer.onError(it) }) { newConsumer.onComplete() }
    }

    @Synchronized
    fun detachEventConsumer(consumerToRemove: Observer<TEventType>?) {
        if (consumerTrackingMap.containsKey(consumerToRemove)) {

            // Remove the reference from the tracking map and then
            // call dispose to stop the flow of events.
            consumerTrackingMap.remove(consumerToRemove)!!.dispose()
        }
    }
}