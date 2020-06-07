package com.pluralsight.rxjava2.utility.subscribers

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.utility.GateBasedSynchronization
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class SingleDemoSubscriber<TEvent> @JvmOverloads constructor(
        private val gate: GateBasedSynchronization = GateBasedSynchronization(),
        private val errorGateName: String = "onError",
        private val successGateName: String = "onSuccess"
) : SingleObserver<TEvent> {

    override fun onSuccess(t: TEvent) {
        log.info("onSuccess : $t")
        gate.openGate(successGateName)
    }

    override fun onSubscribe(d: Disposable) {
        log.info("onSubscribe")
    }

    override fun onError(e: Throwable) {
        log.error("onError : ${e.message}")
        log.error(e.message, e)
        gate.openGate(errorGateName)
    }

}

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