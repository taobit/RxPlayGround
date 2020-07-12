package com.pluralsight.rxjava2.nitrite.datasets

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.nitrite.NitriteSchema
import com.pluralsight.rxjava2.nitrite.entity.FibonacciNumber
import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence
import org.dizitart.no2.Nitrite
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class NitriteFibonacciSequenceSchema : NitriteSchema {
    override fun applySchema(nitriteDatabase: Nitrite) {
        val fibonacciRepo = nitriteDatabase.getRepository(FibonacciNumber::class.java)
        val counter = AtomicInteger()
        if (fibonacciRepo.find().totalCount() == 0) {
            FibonacciSequence.create(300).subscribe {
                if (counter.incrementAndGet() % 1000 == 0) {
                    log.info("Fibonacci numbers generating: {}", counter.get())
                }
                fibonacciRepo.insert(FibonacciNumber(UUID.randomUUID(), it))
            }
        }
    }
}