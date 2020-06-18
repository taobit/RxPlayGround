package com.pluralsight.rxjava2.utility

import com.pluralsight.rxjava2.module2.log
import java.util.concurrent.TimeUnit

fun sleep(duration: Long, unit: TimeUnit) {
    try {
        log.info("Sleeping for : ${unit.toMillis(duration)}")
        unit.sleep(duration)
    } catch (e: Exception) {
        log.error(e.message)
    }
}