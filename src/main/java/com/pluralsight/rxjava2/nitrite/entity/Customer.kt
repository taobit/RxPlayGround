package com.pluralsight.rxjava2.nitrite.entity

import org.dizitart.no2.objects.Id
import java.io.Serializable
import java.util.*

data class Customer(@JvmField @Id var customerId: UUID, var firstName: String, var lastName: String) : Serializable {

    override fun toString(): String {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}'
    }
}