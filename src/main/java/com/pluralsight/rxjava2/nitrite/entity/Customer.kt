package com.pluralsight.rxjava2.nitrite.entity

import org.dizitart.no2.objects.Id
import java.io.Serializable
import java.util.*

data class Customer @JvmOverloads constructor(@Id var customerId: UUID? = null,
                                              var firstName: String? = null,
                                              var lastName: String? = null) : Serializable {

    override fun toString(): String {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}'
    }
}