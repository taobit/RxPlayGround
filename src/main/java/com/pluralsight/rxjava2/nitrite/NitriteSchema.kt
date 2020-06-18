package com.pluralsight.rxjava2.nitrite

import org.dizitart.no2.Nitrite

interface NitriteSchema {
    fun applySchema(nitriteDatabase: Nitrite?)
}