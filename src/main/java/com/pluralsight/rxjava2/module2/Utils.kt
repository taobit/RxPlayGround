package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.GateBasedSynchronization
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Utils

@JvmField
val log: Logger = LoggerFactory.getLogger(Utils.javaClass)

fun logNext(message: Any) = log.info("onNext : $message")
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

fun <T : Any> Observable<T>.simplySubscribe() = subscribeBy(
    onNext = { logNext(it) },
    onError = { log.error(it.message) },
    onComplete = { log.info("onComplete") }
)

fun <T : Any> Observable<T>.simplySubscribeByGate() = subscribeBy(
    onNext = {
        logNext(it)
    },

    onError = { log.error(it.message) },
    onComplete = { log.info("onComplete") }
)

fun GateBasedSynchronization.onError(e: Throwable) {
    log.error("onError : ${e.message}")
    openGate("onError")
}

fun GateBasedSynchronization.onSuccess(any: Any) {
    log.info("onSuccess : $any")
    openGate("onSuccess")
}

fun GateBasedSynchronization.onComplete() {
    log.info("onComplete")
    openGate("onComplete")
}

fun GateBasedSynchronization.onSubscribe(){
    log.info("onSubscribe")
}