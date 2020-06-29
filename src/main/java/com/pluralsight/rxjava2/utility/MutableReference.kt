package com.pluralsight.rxjava2.utility

class MutableReference<TContainedType>(
        var containedValue: TContainedType? = null
) {
    @Suppress("unused")
    fun hasValue(): Boolean {
        return containedValue != null
    }

}