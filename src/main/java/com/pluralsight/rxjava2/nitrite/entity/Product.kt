package com.pluralsight.rxjava2.nitrite.entity

import org.dizitart.no2.objects.Id
import java.io.Serializable
import java.util.*

class Product(@Id var productId: UUID? = null,
              var productName: String? = null,
              var productSKU: String? = null) : Serializable {

    override fun toString(): String {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productSKU='" + productSKU + '\'' +
                '}'
    }
}