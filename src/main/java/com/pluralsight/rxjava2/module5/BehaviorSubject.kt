package com.pluralsight.rxjava2.module5

import com.pluralsight.rxjava2.module2.log
import io.reactivex.rxjava3.subjects.BehaviorSubject

fun main() {
    BehaviorSubject.createDefault("omega")
            .apply {
                subscribe { log.info("onNext : $it") }.dispose()
                onNext("alpha")
                onNext("beta")
                onNext("gamma")
                subscribe { log.info("onNext : $it") }.dispose()
            }
}