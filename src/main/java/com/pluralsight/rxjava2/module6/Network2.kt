package com.pluralsight.rxjava2.module6

import com.pluralsight.rxjava2.module2.OnComplete
import com.pluralsight.rxjava2.module2.OnError
import com.pluralsight.rxjava2.module2.gate
import com.pluralsight.rxjava2.utility.network.HttpResponseObserverFactory
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.URI
import java.util.concurrent.TimeUnit

fun main() {
    val request1 = HttpResponseObserverFactory
            .additionRequestResponseObservable(URI(URL1))
            .subscribeOn(Schedulers.io())
    val request2 = HttpResponseObserverFactory
            .additionRequestResponseObservable(URI(URL2))
            .subscribeOn(Schedulers.io())
    Observable.mergeArray(2, 1, request1, request2)
            .subscribeOn(Schedulers.io())
            .timeout(5L, TimeUnit.SECONDS, Observable.just(-1))
            .subscribe(DemoSubscriber<Int>())
    gate.waitForAny(OnError, OnComplete)
}

private const val URL1 = "http://localhost:22221/addition?a=5&b=9&delay=6000"
private const val URL2 = "http://localhost:22221/addition?a=21&b=21&delay=0"