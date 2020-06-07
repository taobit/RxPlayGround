package com.pluralsight.rxjava2.utility.subscribers

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.utility.GateBasedSynchronization
import com.pluralsight.rxjava2.utility.ThreadHelper
import io.reactivex.observers.ResourceObserver
import java.util.concurrent.TimeUnit

class DemoSubscriber<TEvent> @JvmOverloads constructor(
        private val gates: GateBasedSynchronization = GateBasedSynchronization(),
        private val onNextDelayDuration: Long = 0L,
        private val onNextDelayTimeUnit: TimeUnit = TimeUnit.SECONDS,
        private val errorGateName: String = "onError",
        private val completeGateName: String = "onComplete"
) : ResourceObserver<TEvent>() {

    override fun onNext(event: TEvent) {
        log.info("onNext - {}", event?.toString() ?: "<NULL>")

        // Drag our feet if requested to do so...
        if (onNextDelayDuration > 0) {
            ThreadHelper.sleep(onNextDelayDuration, onNextDelayTimeUnit)
        }
    }

    override fun onError(e: Throwable) {
        log.error("onError - {}", e.message)
        gates.openGate(errorGateName)
    }

    override fun onComplete() {
        log.info("onComplete")
        gates.openGate(completeGateName)
    }
}