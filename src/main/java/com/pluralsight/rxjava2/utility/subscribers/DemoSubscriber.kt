package com.pluralsight.rxjava2.utility.subscribers

import com.pluralsight.rxjava2.module2.gate
import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.module2.onComplete
import com.pluralsight.rxjava2.module2.onError
import com.pluralsight.rxjava2.utility.GateBasedSynchronization
import com.pluralsight.rxjava2.utility.sleep
import io.reactivex.rxjava3.observers.ResourceObserver
import java.util.concurrent.TimeUnit

class DemoSubscriber<TEvent> @JvmOverloads constructor(
        private val gates: GateBasedSynchronization = gate,
        private val onNextDelayDuration: Long = 0L,
        private val onNextDelayTimeUnit: TimeUnit = TimeUnit.SECONDS
) : ResourceObserver<TEvent>() {

    override fun onNext(event: TEvent) {
        log.info("onNext $event")
        if (onNextDelayDuration > 0) {
            sleep(onNextDelayDuration, onNextDelayTimeUnit)
        }
    }

    override fun onError(e: Throwable) = gates.onError(e)


    override fun onComplete() = gates.onComplete()
}