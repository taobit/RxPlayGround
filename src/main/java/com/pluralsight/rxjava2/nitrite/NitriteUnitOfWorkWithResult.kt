package com.pluralsight.rxjava2.nitrite

import org.dizitart.no2.Nitrite

@FunctionalInterface
interface NitriteUnitOfWorkWithResult<T> {
    @Throws(Exception::class)
    fun apply(database: Nitrite?): T
}