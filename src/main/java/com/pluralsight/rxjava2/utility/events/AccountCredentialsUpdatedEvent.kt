package com.pluralsight.rxjava2.utility.events

class AccountCredentialsUpdatedEvent(private val accountEmail: String) : EventBase() {
    override fun toString(): String {
        return "AccountCredentialsUpdatedEvent{" +
                "accountEmail='" + accountEmail + '\'' +
                "} " + super.toString()
    }

}