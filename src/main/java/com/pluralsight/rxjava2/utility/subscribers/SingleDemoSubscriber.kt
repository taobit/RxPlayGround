package com.pluralsight.rxjava2.utility.subscribers

import com.pluralsight.rxjava2.module2.onError
import com.pluralsight.rxjava2.module2.onSubscribe
import com.pluralsight.rxjava2.module2.onSuccess
import com.pluralsight.rxjava2.utility.GateBasedSynchronization
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

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