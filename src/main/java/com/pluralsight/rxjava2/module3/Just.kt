package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.core.Observable
import kotlin.system.exitProcess

fun main() {
    Observable.just(42).subscribe(DemoSubscriber())
    exitProcess(0)
}