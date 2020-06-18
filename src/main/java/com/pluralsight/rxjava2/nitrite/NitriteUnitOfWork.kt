package com.pluralsight.rxjava2.nitrite

import org.dizitart.no2.Nitrite

interface NitriteUnitOfWork {
    @Throws(Exception::class)
    fun apply(database: Nitrite?)
}