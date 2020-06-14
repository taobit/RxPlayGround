package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence.toArray
import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence.toArrayList
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask
import kotlin.system.exitProcess

fun main() {
    val firstFiveFibonacciNumbers = toArray(5)

    // example of "fromArray" using an array of 5 integers.
    log.info("fromArray")
    var targetObservable = Observable.fromArray(*firstFiveFibonacciNumbers)
    targetObservable.subscribe(DemoSubscriber())
    log.info("-----------------------------------------------------------------------------------")
    log.info("fromIterable")

    // example of "fromIterable" using an array of 7 integers
    val fibonacciArray = toArrayList(7) as ArrayList<Long>
    targetObservable = Observable.fromIterable(fibonacciArray)
    targetObservable.subscribe(DemoSubscriber())
    log.info("-----------------------------------------------------------------------------------")
    log.info("fromCallable")

    // example of "fromCallable" using an array of 9 integers
    var workObservable = Observable.fromCallable { toArray(9) }

    // Note that the fromCallable method only returns a single value in the return Observable.
    targetObservable = Observable.fromArray(*workObservable.blockingSingle())
    targetObservable.subscribe(DemoSubscriber())
    log.info("-----------------------------------------------------------------------------------")
    log.info("fromFuture")

    // example of Observable creation via "fromFuture" using an array of 6 integers

    // Create a FutureTask that will return an Integer array of 6 elements
    val futureTask = FutureTask(Callable { toArray(6) })

    // Create an ExecutorService that has a single thread.
    val executor = Executors.newFixedThreadPool(1)

    // Execute the futureTask to generate the fibonacci sequence...but this will be on a different
    // thread.
    executor.execute(futureTask)
    // Take in the FutureTask (Future as it's base class) and makeObservable an Observable<Integer[]>
    // based on it's result.
    workObservable = Observable.fromFuture(futureTask)

    // Block to pull out the data from the future and pass into an Observable<Integer>
    // using fromArray
    targetObservable = Observable.fromArray(*workObservable.blockingSingle())
    targetObservable.subscribe(DemoSubscriber())
    exitProcess(0)
}