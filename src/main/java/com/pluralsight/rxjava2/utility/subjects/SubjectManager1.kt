package com.pluralsight.rxjava2.utility.subjects

import com.pluralsight.rxjava2.module2.log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import java.util.*

class SubjectManager<any : Any> {
    private val subjectMap: MutableMap<String, NamedSubject<any>>
    fun registerSubject(namedSubject: NamedSubject<any>) {
        log.info(namedSubject.toString())
        subjectMap[namedSubject.subjectName] = namedSubject
    }

    fun deregisterSubject(namedSubject: NamedSubject<any>) {
        subjectMap.remove(namedSubject.subjectName)
    }

    fun addEventProducer(subjectName: String, observable: Observable<any>) {
        if (subjectMap.containsKey(subjectName)) {
            subjectMap[subjectName]?.addEventProducer(observable)
        }
    }

    fun addEventConsumer(subjectName: String, observer: Observer<any>) {
        if (subjectMap.containsKey(subjectName)) {
            subjectMap[subjectName]?.addEventConsumer(observer)
        }
    }

    init {
        subjectMap = HashMap()
    }
}