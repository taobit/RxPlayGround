package com.pluralsight.rxjava2.nitrite.entity

import org.dizitart.no2.IndexType
import org.dizitart.no2.objects.Id
import org.dizitart.no2.objects.Index
import org.dizitart.no2.objects.Indices
import java.util.*

@Indices(Index(value = "customerId", type = IndexType.NonUnique), Index(value = "productId", type = IndexType.NonUnique))
class CustomerProductPurchaseHistory(@Id var customerProductPurchaseHistoryId: UUID? = null, var customerId: UUID? = null, var productId: UUID? = null) {

    override fun toString(): String {
        return "CustomerProductPurchaseHistory{" +
                "customerProductPurchaseHistoryId=" + customerProductPurchaseHistoryId +
                ", customerId=" + customerId +
                ", productId=" + productId +
                '}'
    }
}