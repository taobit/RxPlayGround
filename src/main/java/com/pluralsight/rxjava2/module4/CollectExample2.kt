package com.pluralsight.rxjava2.module4

import com.pluralsight.rxjava2.utility.MutableReference
import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence.create
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.functions.Supplier
import org.slf4j.LoggerFactory

object CollectExample2 {
    private val log = LoggerFactory.getLogger(CollectExample2::class.java)

    @JvmStatic
    fun main(args: Array<String>) {

        // collect is also useful for accumulating values or state information.  Here
        // we will total up the first 12 numbers of the Fibonacci sequence.
        val sum = create(12)
                .collect( // What is the initial state?  In this case we makeObservable a container
                        // for an integer.
                        { MutableReference(0) },  // The collection function.  Sum the next number into the MutableReference
                        { mutableReference: MutableReference<Long>, nextValue: Long -> mutableReference.setValue(mutableReference.getValue() + nextValue) }
                ) // We block and get the value out of the Single that was returned
                // by the collect operation.
                .blockingGet()
                .getValue()


        // Emit the sum
        log.info(java.lang.Long.toString(sum))
        System.exit(0)
    }
}