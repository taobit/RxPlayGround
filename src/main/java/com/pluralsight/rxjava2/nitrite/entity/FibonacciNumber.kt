package com.pluralsight.rxjava2.nitrite.entity

import org.dizitart.no2.IndexType
import org.dizitart.no2.objects.Id
import org.dizitart.no2.objects.Index
import org.dizitart.no2.objects.Indices
import java.io.Serializable
import java.util.*

@Indices(Index(value = "numberValue", type = IndexType.Unique))
class FibonacciNumber(@Suppress("unused") @Id var fibonacciNumberId: UUID? = null, var numberValue: Long = 0L) : Serializable {
}