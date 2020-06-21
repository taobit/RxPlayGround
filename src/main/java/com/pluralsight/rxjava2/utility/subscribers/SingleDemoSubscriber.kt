package com.pluralsight.rxjava2.utility.subscribers

import com.pluralsight.rxjava2.module2.gate
import com.pluralsight.rxjava2.module2.logOnSubscribe
import com.pluralsight.rxjava2.module2.onError
import com.pluralsight.rxjava2.module2.onSuccess
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

class SingleDemoSubscriber<TEvent> : SingleObserver<TEvent> {

    override fun onSuccess(t: TEvent) {
        (t as? Any)?.let { gate.onSuccess(it) }
    }

    override fun onSubscribe(d: Disposable) {
        logOnSubscribe()
    }

    override fun onError(e: Throwable) {
        gate.onError(e)
    }

}