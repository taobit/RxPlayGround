package com.pluralsight.rxjava2.module6

import com.pluralsight.rxjava2.module2.OnComplete
import com.pluralsight.rxjava2.module2.OnError
import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.nitrite.NitriteSchema
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


private const val RAIL_COUNT = 3


fun main() {

    // Since we will have 3 threads in parallel ("rails"), makeObservable 3 synchonization gate
    // collections.
    val gates = Array(RAIL_COUNT) { GateBasedSynchronization() }
    try {

        // Create a schema object that we will apply to our test
        // database.  In this case, it will makeObservable a collection that
        // contains the Fibonacci sequence.
        val schema: NitriteSchema = NitriteFibonacciSequenceSchema()
        NitriteTestDatabase(schema).use { testDatabase ->

            // We are going to makeObservable a ParallelFlowable in order to use our flow
            // controlled observable, but process the messages in parallel.  We
            // start with our Flowable as in the last example.
            val fibonacciParallelFlowable =  // This time we want a ParallelFlowable
                    ParallelFlowable.from( // .. using the same flowable Fibonacci sequence.
                            FibonacciNumberDataAccess.selectAsFlowable(testDatabase.database) // We want to observe on the IO scheduler...this also cuts the buffer
                                    // size down to 3 x RAIL_COUNT
                                    .observeOn(Schedulers.io(), false, 3)
                            , RAIL_COUNT
                    ) // ParallelFlowable.runOn method to set where the parallel tasks
                            // will take place.
                            .runOn(Schedulers.io(), 1)

            // To subscribe to a parallel flowable, you must provide a FlowableSubscriber
            // per rail.
            val subscriberRails: Array<FlowableSubscriber<Long>?> = Array(RAIL_COUNT) {
                object : FlowableSubscriber<Long> {
                    val gate = gates[it]
                    var subscription: Subscription? = null

                    override fun onSubscribe(s: Subscription?) {
                        subscription = s?.apply {
                            request(RAIL_COUNT.toLong())
                        }
                    }

                    override fun onNext(t: Long?) {
                        sleep(50, TimeUnit.MILLISECONDS)

                        // Log the next number
                        log.info("Next Fibonacci Number: {} - {}", t, it)
                        if (it % RAIL_COUNT == 0) {
                            subscription?.request(RAIL_COUNT.toLong())
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

            }

            // Finally...we subscribe and watch the magic.  We pass in
            // our FlowableSubscribers array, one for each parallel rail.
            fibonacciParallelFlowable.subscribe(subscriberRails)

            // Wait for things to finish.
            GateBasedSynchronization.waitMultiple(arrayOf("onError", "onComplete"), *gates)
        }
    } catch (t: Throwable) {
        log.error(t.message, t)
    }
    exitProcess(0)
}
