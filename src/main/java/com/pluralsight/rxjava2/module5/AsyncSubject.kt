package com.pluralsight.rxjava2.module5

import com.pluralsight.rxjava2.module2.OnComplete
import com.pluralsight.rxjava2.module2.gate
import com.pluralsight.rxjava2.module2.onComplete
import com.pluralsight.rxjava2.utility.sleep
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.AsyncSubject
import java.util.concurrent.TimeUnit

fun main() {
    AsyncSubject.create<String>().apply {
        val action = Runnable {
            sleep(2, TimeUnit.SECONDS)
            onNext("Hello World 1")
            onNext("Hello World 2")
            onNext("Hello World 3")
            sleep(1, TimeUnit.SECONDS)
            onComplete()
            gate.onComplete()
        }
        subscribeOn(Schedulers.computation())
                .subscribe(DemoSubscriber<String>())
        Schedulers.io().scheduleDirect(action)
        gate.waitForAny(OnComplete)
    }
}