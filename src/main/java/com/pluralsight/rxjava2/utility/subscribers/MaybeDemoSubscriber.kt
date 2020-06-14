package com.pluralsight.rxjava2.utility.subscribers

import com.pluralsight.rxjava2.module2.*
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.disposables.Disposable

class MaybeDemoSubscriber<TEvent : Any> : MaybeObserver<TEvent> {

    override fun onSubscribe(d: Disposable) {
        log.info("onSubscribe")
    }

    override fun onSuccess(tEvent: TEvent) {
        gate.onSuccess(tEvent)
    }

    override fun onError(e: Throwable) {
        gate.onError(e)
    }

    override fun onComplete() {
        gate.onComplete()
    }
}