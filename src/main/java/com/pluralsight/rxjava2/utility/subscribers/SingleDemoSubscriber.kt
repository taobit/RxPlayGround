package com.pluralsight.rxjava2.utility.subscribers

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.utility.GateBasedSynchronization
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class SingleDemoSubscriber<TEvent> @JvmOverloads constructor(
        private val gate: GateBasedSynchronization = GateBasedSynchronization()
) : SingleObserver<TEvent> {

    override fun onSuccess(t: TEvent) {
        (t as? Any)?.let { gate.onSuccess(it) }
    }

    override fun onSubscribe(d: Disposable) {
        gate.onSubscribe()
    }

    override fun onError(e: Throwable) {
        gate.onError(e)
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