package com.pluralsight.rxjava2.utility

import java.util.*

class MutableReference<TContainedType>(
        containedValue: TContainedType? = null,
        private var value: Optional<TContainedType> = Optional.ofNullable(containedValue)
) {
    fun hasValue(): Boolean {
        return value.isPresent
    }

    fun getValue(): TContainedType {
        return value.get()
    }

    fun getValue(defaultValue: TContainedType): TContainedType {
        return value.orElse(defaultValue)
    }

    fun setValue(newValue: TContainedType) {
        value = Optional.of(newValue)
    }

    override fun toString() = value.toString()
}