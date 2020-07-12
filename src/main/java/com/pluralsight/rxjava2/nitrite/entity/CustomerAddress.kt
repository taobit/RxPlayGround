package com.pluralsight.rxjava2.nitrite.entity

import org.dizitart.no2.IndexType
import org.dizitart.no2.objects.Id
import org.dizitart.no2.objects.Index
import org.dizitart.no2.objects.Indices
import java.io.Serializable
import java.util.*

@Indices(Index(value = "customerId", type = IndexType.NonUnique))
class CustomerAddress (@Id var customerAddressId: UUID? = null,
                                  var customerId: UUID? = null,
                                  var addressLine1: String? = null,
                                  var addressLine2: String? = null,
                                  var city: String? = null,
                                  var state: String? = null,
                                  var postalCode: String? = null) : Serializable {

    override fun toString(): String {
        return "CustomerAddress{" +
                "customerAddressId=" + customerAddressId +
                ", customerId=" + customerId +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}'
    }
}