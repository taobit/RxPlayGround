package com.pluralsight.rxjava2.utility.events

import java.util.*

open class EventBase @JvmOverloads constructor() {
    private val eventUUID: UUID = UUID.randomUUID()

    override fun toString(): String {
        return "EventBase{" +
                "eventUUID=" + eventUUID +
                '}'
    }
}