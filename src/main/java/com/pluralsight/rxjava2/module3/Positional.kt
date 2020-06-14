package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.module2.runCode
import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence
import com.pluralsight.rxjava2.utility.subscribers.MaybeDemoSubscriber
import com.pluralsight.rxjava2.utility.subscribers.SingleDemoSubscriber
import io.reactivex.rxjava3.core.Observable

fun main() {
    runCode("First") {
        FibonacciSequence.create(10)
                .first(99999L)
                .subscribe(SingleDemoSubscriber<Long>())
    }
    runCode("First With Default") {
        Observable.empty<Long>()
                .first(9999L)
                .subscribe(SingleDemoSubscriber<Long>())
    }
    runCode("First Or Error") {
        Observable.empty<Long>()
                .firstOrError()
                .subscribe(SingleDemoSubscriber<Long>())
    }
    runCode("First Maybe") {
        FibonacciSequence.create(10)
                .firstElement()
                .subscribe(MaybeDemoSubscriber())
    }
    runCode("first element empty") {
        Observable.empty<Long>()
                .firstElement()
                .subscribe(MaybeDemoSubscriber())
    }
    runCode("Last") {
        FibonacciSequence.create(10)
                .last(99999L)
                .subscribe(SingleDemoSubscriber<Long>())
    }
    runCode("Last Empty") {
        Observable.empty<Long>()
                .last(99999L)
                .subscribe(SingleDemoSubscriber<Long>())
    }
    runCode("Last Or Error") {
        Observable.empty<Long>()
                .lastOrError()
                .subscribe(SingleDemoSubscriber<Long>())
    }
    runCode("Element At") {
        FibonacciSequence.create(10)
                .elementAt(5, 9999L)
                .subscribe(SingleDemoSubscriber<Long>())
    }
}