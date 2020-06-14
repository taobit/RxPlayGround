package com.pluralsight.rxjava2.utility

object MemoryHelper {
    fun totalMemory(): Long {
        return Runtime.getRuntime().totalMemory()
    }

    private fun freeMemory(): Long {
        return Runtime.getRuntime().freeMemory()
    }

    fun freeMemory(divisor: Long, decimalPlaces: Int): String {
        val decimalAmount = freeMemory().toFloat() / divisor.toFloat()
        return String.format("%1$,." + decimalPlaces + "f", decimalAmount)
    }
}