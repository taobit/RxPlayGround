package com.pluralsight.rxjava2.module5

import com.pluralsight.rxjava2.utility.events.EventBase
import com.pluralsight.rxjava2.utility.sleep
import com.pluralsight.rxjava2.utility.subjects.NamedSubject
import com.pluralsight.rxjava2.utility.subjects.SubjectManager
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

fun main() {
    SubjectManager<EventBase>().apply {
        registerSubject(NamedSubject(USER_SERVICE_SUBJECT_NAME, PublishSubject.create()))
        registerSubject(NamedSubject(COMMENT_SERVICE_SUBJECT_NAME, PublishSubject.create()))

        addEventConsumer(USER_SERVICE_SUBJECT_NAME, DemoSubscriber<EventBase>())
        addEventConsumer(COMMENT_SERVICE_SUBJECT_NAME, DemoSubscriber<EventBase>())

        addEventProducer(USER_SERVICE_SUBJECT_NAME,
                UserServiceEventObservable.userServiceEventGenerator().subscribeOn(Schedulers.io()))

        addEventProducer(COMMENT_SERVICE_SUBJECT_NAME,
                CommentServiceEventObservable.commentServiceEventGenerator().subscribeOn(Schedulers.io()))
    }
    sleep(10, TimeUnit.SECONDS)
    exitProcess(0)
}

private const val USER_SERVICE_SUBJECT_NAME = "UserServiceEventSubject"
private const val COMMENT_SERVICE_SUBJECT_NAME = "CommentServiceEventSubject"