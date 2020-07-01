package com.pluralsight.rxjava2.module6

import com.pluralsight.rxjava2.module2.OnComplete
import com.pluralsight.rxjava2.module2.OnError
import com.pluralsight.rxjava2.module2.gate
import com.pluralsight.rxjava2.module2.logError
import com.pluralsight.rxjava2.utility.network.HttpResponseObserverFactory
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.URI
import java.util.concurrent.TimeUnit

fun main() {
    try {
        // Use our HttpResponseObserverFactory to makeObservable an Observable that returns
        // the result of the call to the addition service for the first request.
        // Note that we are placing this request on the IO thread pool since it
        // will be waiting on IO predominantly.
        val request1 = HttpResponseObserverFactory
                .additionRequestResponseObservable(URI(URL1))
                .subscribeOn(Schedulers.io())
        val request2 = HttpResponseObserverFactory
                .additionRequestResponseObservable(URI(URL2))
                .subscribeOn(Schedulers.io())
        // We use the merge operator with maxConcurrency of 2 in order
        // to cause both networkRequest1 and networkRequest2 to be executed
        // simultaneously.  We want all of this on the IO threads.
        // We also set a timeout of 5 seconds for the requests.
        Observable.mergeArray(
                2, 1, request1, request2
        ).subscribeOn(Schedulers.io())
                .timeout(5L, TimeUnit.SECONDS)
                .subscribe(DemoSubscriber<Int>())
        gate.waitForAny(OnComplete, OnError)
    } catch (t: Throwable) {
        logError(t)
    }
}

private const val URL1 = "http://localhost:22221/addition?a=5&b=9&delay=4000"
private const val URL2 = "http://localhost:22221/addition?a=21&b=21&delay=0"