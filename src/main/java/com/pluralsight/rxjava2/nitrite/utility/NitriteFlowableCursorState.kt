package com.pluralsight.rxjava2.nitrite.utility

import org.dizitart.no2.objects.Cursor

class NitriteFlowableCursorState<TEntityType>(cursor: Cursor<TEntityType>) {
    val iterator: Iterator<TEntityType>

    init {
        iterator = cursor.iterator()
    }
}