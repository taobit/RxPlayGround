package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.GateBasedSynchronization
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Utils

@JvmField
val log: Logger = LoggerFactory.getLogger(Utils.javaClass)

fun logNext(message: String) = log.info("onNext : $message")
fun logError(throwable: Throwable) = log.error("onError : ${throwable.message}")

// keep thread running until finish all test
val gate = GateBasedSynchronization()
fun runCode(name: String, function: () -> Unit) {
    log.info("*********************")
    log.info(name)
    log.info("---------------------")
    function.invoke()
    log.info("*********************\n")
}