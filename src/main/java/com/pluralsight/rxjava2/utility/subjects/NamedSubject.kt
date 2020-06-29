package com.pluralsight.rxjava2.utility.subjects

import io.reactivex.rxjava3.subjects.Subject

class NamedSubject<TEventType> @JvmOverloads constructor(val subjectName: String,
                                                         @Suppress("unused") private val subjectToUse: Subject<TEventType>? = null) :
        SelectableSubject<TEventType>() {
    override fun toString() = subjectName + " " + subjectToUse.toString()

}