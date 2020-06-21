package com.pluralsight.rxjava2.module4

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.module2.logError
import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence
import io.reactivex.rxjava3.kotlin.subscribeBy

fun main() {
    FibonacciSequence.create(20)
            .groupBy {
                if (it % 2 == 0L) Type.ODD
                else Type.EVEN
            }
            .subscribe { it ->
                val head = it.key.name
                val builder = StringBuilder()
                it.subscribeBy(
                        onNext = {
                            if (builder.isNotEmpty()) {
                                builder.append(", ")
                            }
                            builder.append(it)
                        },
                        onError = { logError(it) },
                        onComplete = {
                            log.info("$head: $builder")
                            builder.clear()
                        }
                )
            }
}

enum class Type {
    ODD,
    EVEN
}