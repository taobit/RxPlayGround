package com.pluralsight.rxjava2.utility.subjects

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

open class SelectableSubject<EventType> @JvmOverloads constructor(private val subject: Subject<EventType> = PublishSubject.create()) {
    private val producerTrackingMap = mutableMapOf<Observable<EventType>, Disposable>()
    private val consumerTrackingMap = mutableMapOf<Observer<EventType>, Disposable>()

    @Synchronized
    fun addEventProducer(newEventSource: Observable<EventType>) {
        newEventSource
            .doOnSubscribe {
                producerTrackingMap[newEventSource] = it
                subject.onSubscribe(it)
            }
            .subscribe(
                { subject.onNext(it) },
                { subject.onError(it) },
                { subject.onComplete() }
            )
    }

    @Synchronized
    fun removeEventProducer(eventSource: Observable<EventType>) {
        if (producerTrackingMap.containsKey(eventSource)) {
            producerTrackingMap.remove(eventSource)?.dispose()
        }
    }

    @Synchronized
    fun addEventConsumer(newConsumer: Observer<EventType>) {
        subject
            .doOnSubscribe {
                consumerTrackingMap[newConsumer] = it
                subject.onSubscribe(it)
            }
            .subscribe(
                { subject.onNext(it) },
                { subject.onError(it) },
                { subject.onComplete() }
            )
    }

    @Synchronized
    fun detachEventConsumer(consumer: Observer<EventType>) {
        if (consumerTrackingMap.containsKey(consumer)) {
            consumerTrackingMap.remove(consumer)?.dispose()
        }
    }

}