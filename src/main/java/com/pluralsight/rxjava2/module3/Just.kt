package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.Observable
import kotlin.system.exitProcess

fun main() {
    val observable = Observable.just(42)
    observable.subscribe(DemoSubscriber())
    exitProcess(0)
}