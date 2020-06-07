package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.module2.runCode
import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.Observable
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask
import kotlin.system.exitProcess

fun main() {
    runCode("from Array") {
        val list = FibonacciSequence.toArrayList(5)
        Observable.fromIterable(list).subscribe(DemoSubscriber<Long>())
    }

    runCode("from Iterable") {
        val array = FibonacciSequence.toArrayList(5)
        Observable.fromIterable(array).subscribe(DemoSubscriber<Long>())
    }

    runCode("from Callable") {
        val workObservable = Observable.fromCallable { FibonacciSequence.toArray(5) }
        Observable.fromArray(*workObservable.blockingSingle()).subscribe(DemoSubscriber<Long>())
    }

    runCode("from future") {
        val futureTask = FutureTask { FibonacciSequence.toArray(5) }
        Executors.newFixedThreadPool(1).execute(futureTask)
        Observable.fromFuture(futureTask)
                .also {
                    Observable.fromArray(*it.blockingSingle()).subscribe(DemoSubscriber<Long>())
                }
    }
    exitProcess(0)
}