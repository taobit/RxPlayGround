package com.pluralsight.rxjava2.utility.subjects

import io.reactivex.rxjava3.subjects.Subject

class NamedSubject<TEventType>(val subjectName: String, private val subjectToUse: Subject<TEventType>? = null) :
        SelectableSubject<TEventType>()