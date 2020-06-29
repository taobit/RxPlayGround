package com.pluralsight.rxjava2.module4

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.utility.MutableReference
import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence.create
import kotlin.system.exitProcess

fun main() {

    // collect is also useful for accumulating values or state information.  Here
    // we will total up the first 12 numbers of the Fibonacci sequence.
    val sum = create(12)
            .collect( // What is the initial state?  In this case we makeObservable a container
                    // for an integer.
                    { MutableReference(0) },  // The collection function.  Sum the next number into the MutableReference
                    { mutableReference: MutableReference<Long>, nextValue: Long -> mutableReference.containedValue = mutableReference.containedValue?.plus(nextValue) }
            ) // We block and get the value out of the Single that was returned
            // by the collect operation.
            .blockingGet()
            .containedValue


    // Emit the sum
    log.info(sum?.toString())
    exitProcess(0)
}
