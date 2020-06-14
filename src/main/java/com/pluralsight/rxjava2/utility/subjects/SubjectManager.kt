package com.pluralsight.rxjava2.utility.subjects

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer

class SubjectManager<EventType : Any> {
    private val subjectMap: HashMap<String, NamedSubject<EventType>> = HashMap()
    fun registerSubject(namedSubject: NamedSubject<EventType>) {
        subjectMap[namedSubject.subjectName] = namedSubject
    }

    fun deregisterSubject(namedSubject: NamedSubject<*>) {
        subjectMap.remove(namedSubject.subjectName)
    }

    fun addEventProducer(subjectName: String, observable: Observable<EventType>) {
        if (subjectMap.containsKey(subjectName)) {
            observable.let { subjectMap[subjectName]?.addEventProducer(it) }
        }
    }

    fun addEventConsumer(subjectName: String, observer: Observer<EventType>) {
        if (subjectMap.containsKey(subjectName)) {
            subjectMap[subjectName]?.addEventConsumer(observer)
        }
    }

}