package com.pluralsight.rxjava2.module6

import com.pluralsight.rxjava2.module2.*
import com.pluralsight.rxjava2.nitrite.NitriteTestDatabase
import com.pluralsight.rxjava2.nitrite.dataaccess.FibonacciNumberDataAccess
import com.pluralsight.rxjava2.nitrite.datasets.NitriteFibonacciSequenceSchema
import com.pluralsight.rxjava2.utility.sleep
import io.reactivex.rxjava3.core.FlowableSubscriber
import io.reactivex.rxjava3.schedulers.Schedulers
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    try {
        val schema = NitriteFibonacciSequenceSchema()
        NitriteTestDatabase(schema).also {
            FibonacciNumberDataAccess.selectAsFlowable(it.database)
                    .observeOn(Schedulers.computation(), false, 3)
                    .subscribeOn(Schedulers.io())
                    .subscribe(object : FlowableSubscriber<Long> {

                        private var counter = AtomicInteger()
                        private var subscription: Subscription? = null

                        override fun onSubscribe(s: Subscription?) {
                            subscription = s?.apply {
                                request(3)
                            }
                        }

                        override fun onNext(t: Long?) {
                            sleep(10, TimeUnit.MILLISECONDS)
                            if (counter.incrementAndGet() % 3 == 0) {
                                subscription?.request(3)
                            }
                            log.info("Next Fibonacci Number: {}", t)
                        }

                        override fun onComplete() = gate.onComplete()

                        override fun onError(t: Throwable?) = gate.onError(t)

                    })
        }
        gate.waitForAny(OnComplete, OnError)
    } catch (t: Throwable) {
        logError(t)
    }
}