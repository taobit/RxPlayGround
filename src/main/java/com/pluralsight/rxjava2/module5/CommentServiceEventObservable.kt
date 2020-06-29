package com.pluralsight.rxjava2.module5

import com.pluralsight.rxjava2.utility.MutableReference
import com.pluralsight.rxjava2.utility.events.EventBase
import com.pluralsight.rxjava2.utility.events.NewCommentPostedEvent
import com.pluralsight.rxjava2.utility.sleep
import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.functions.Supplier
import java.util.*
import java.util.concurrent.TimeUnit

object CommentServiceEventObservable {
    private val authorEmails = arrayOf(
            "mrresponsible@aracnid.net",
            "cheater@worldreverse.org",
            "hook@calzonecorner.net",
            "toughguy@unitard.com"
    )

    fun commentServiceEventGenerator(): Observable<EventBase> {
        return Observable.generate(
                Supplier<MutableReference<Int>> { MutableReference() },
                BiConsumer { offset: MutableReference<Int>, eventBaseEmitter: Emitter<EventBase> ->
                    offset.containedValue?.let {
                        // Make sure we haven't run off the end of our list.
                        if (it >= authorEmails.size) {

                            // We have sent all the messages...send the
                            // onComplete event.
                            eventBaseEmitter.onComplete()
                        } else {
                            val nextValue = (it + 1) % authorEmails.size

                            // Send out the next message
                            eventBaseEmitter.onNext(NewCommentPostedEvent(
                                    authorEmails[it],
                                    authorEmails[nextValue],
                                    randomString()
                            ))
                        }

                        // Increment our offset counter
                        offset.containedValue = it + 1

                        // Slow things down
                        sleep(1500, TimeUnit.MILLISECONDS)
                    } ?: kotlin.run { offset.containedValue = 0 }
                })
    }

    private val random = Random()
    private fun randomString(): String {
        val letters = "abcdefghijklmnopqrstuvwxyz "
        val returnBuffer = StringBuilder(64)
        for (i in 0..63) {
            returnBuffer.append(letters[random.nextInt(letters.length)])
        }
        return returnBuffer.toString()
    }
}