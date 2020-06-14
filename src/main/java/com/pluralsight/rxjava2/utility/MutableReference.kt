package com.pluralsight.rxjava2.utility

import java.util.*

class MutableReference<TContainedType>(
        private var value: Optional<TContainedType> = Optional.empty(),
        containedValue: TContainedType? = null
) {

    init {
        containedValue?.let { value = Optional.of(it) }
    }

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
        value = Optional.ofNullable(newValue)
    }
}