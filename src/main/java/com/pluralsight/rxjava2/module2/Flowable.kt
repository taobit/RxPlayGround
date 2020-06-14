package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.sleep
import com.pluralsight.rxjava2.utility.subscribers.onError
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableSubscriber
import io.reactivex.rxjava3.schedulers.Schedulers
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.exitProcess

fun main() {
    val range = Flowable.range(1, 1_000_000_000)
            .repeat()
            .doOnNext { log.info("emitting int : $it") }
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())

    object : FlowableSubscriber<Int> {

        private var counter = AtomicInteger(0)
        private var subscription: Subscription? = null

        override fun onComplete() {
            log.info("onComplete")
            gate.openGate("onComplete")
        }

        override fun onSubscribe(s: Subscription) {
            log.info("onSubscribe")
            subscription = s.apply { request(3) }
        }

        override fun onNext(t: Int?) {
            log.info("onNext : $t")
            sleep(10L, TimeUnit.MILLISECONDS)
            if (counter.incrementAndGet() % 3 == 0) {
                subscription?.request(3)
            }
        }

        override fun onError(t: Throwable) {
            t.let { gate.onError(it) }
        }
    }.also {
        range.subscribe(it)
    }
    gate.waitForAny(20, TimeUnit.SECONDS, "onComplete", "onError")
    exitProcess(0)
}