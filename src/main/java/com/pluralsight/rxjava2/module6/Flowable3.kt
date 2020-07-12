package com.pluralsight.rxjava2.module6

import com.pluralsight.rxjava2.module2.OnComplete
import com.pluralsight.rxjava2.module2.OnError
import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.module2.logError
import com.pluralsight.rxjava2.nitrite.NitriteTestDatabase
import com.pluralsight.rxjava2.nitrite.dataaccess.FibonacciNumberDataAccess
import com.pluralsight.rxjava2.nitrite.datasets.NitriteFibonacciSequenceSchema
import com.pluralsight.rxjava2.utility.GateBasedSynchronization
import com.pluralsight.rxjava2.utility.sleep
import io.reactivex.rxjava3.core.FlowableSubscriber
import io.reactivex.rxjava3.parallel.ParallelFlowable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

fun main() {
    val gates = Array(count) { GateBasedSynchronization() }
    try {
        NitriteTestDatabase(NitriteFibonacciSequenceSchema()).also { database ->
            ParallelFlowable.from(
                    FibonacciNumberDataAccess
                            .selectAsFlowable(database.database)
                            .observeOn(Schedulers.io(), false, 3),
                    count)
                    .runOn(Schedulers.io(), 1)
                    .subscribe(Array(count) {
                        object : FlowableSubscriber<Long> {
                            val gate = gates[it]
                            var subscription: Subscription? = null

                            override fun onSubscribe(s: Subscription?) {
                                subscription = s?.apply {
                                    request(count.toLong())
                                }
                            }

                            override fun onNext(t: Long?) {
                                sleep(50, TimeUnit.MILLISECONDS)

                                // Log the next number
                                log.info("Next Fibonacci Number: {} - {}", t, it)
                                if (it % count == 0) {
                                    subscription?.request(count.toLong())
                                }
                            }

                            override fun onComplete() {
                                log.info(OnComplete)
                                gate.openGate(OnComplete)
                            }

                            override fun onError(t: Throwable?) {
                                log.error(t?.message)
                                gate.openGate(OnError)
                            }

                        }
                    })
            GateBasedSynchronization.waitMultiple(arrayOf(OnError, OnComplete), *gates)
        }
    } catch (t: Throwable) {
        logError(t)
    }
    exitProcess(0)
}

private const val count = 3