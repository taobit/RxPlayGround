package com.pluralsight.rxjava2.module5

import com.pluralsight.rxjava2.utility.MutableReference
import com.pluralsight.rxjava2.utility.events.AccountCredentialsUpdatedEvent
import com.pluralsight.rxjava2.utility.events.EventBase
import com.pluralsight.rxjava2.utility.sleep
import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.functions.Supplier
import java.util.concurrent.TimeUnit

object UserServiceEventObservable {
    private val emailList = arrayOf(
            "test@test.com",
            "harold@pottery.com",
            "lazerus@moonslider.com",
            "mrwaterwings@deepwater.com"
    )

    fun userServiceEventGenerator(): Observable<EventBase> {
        return Observable.generate(
                Supplier<MutableReference<Int>> { MutableReference() },
                BiConsumer { offset: MutableReference<Int>, eventBaseEmitter: Emitter<EventBase> ->
                    offset.containedValue?.let {
                        // Restrict the offset to the size of our email list.
                        if (it >= emailList.size) {

                            // If we are at the end of the list, then send
                            // the onComplete.
                            eventBaseEmitter.onComplete()
                        } else {
                            // We are still in the list...send an update event with
                            // the correct email address.
                            eventBaseEmitter.onNext(AccountCredentialsUpdatedEvent(emailList[it]))
                        }

                        // Increment out array offset
                        offset.containedValue = offset.containedValue!! + 1

                        // Slow things down
                        sleep(1, TimeUnit.SECONDS)
                    } ?: kotlin.run { offset.containedValue = 0 }

                }
        )
    }
}