package com.pluralsight.rxjava2.nitrite.entity

import org.dizitart.no2.objects.Id
import java.io.Serializable
import java.util.*

data class Product(@JvmField @Id var productId: UUID?, var productName: String?, var productSKU: String?) : Serializable {

    override fun toString(): String {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productSKU='" + productSKU + '\'' +
                '}'
    }
}