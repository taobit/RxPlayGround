package com.pluralsight.rxjava2.nitrite.aggregate

import com.pluralsight.rxjava2.nitrite.entity.Customer
import com.pluralsight.rxjava2.nitrite.entity.CustomerAddress
import com.pluralsight.rxjava2.nitrite.entity.Product
import java.util.*

class CustomerAggregate {
    @JvmField
    var customer: Customer? = null
    private val addresses: ArrayList<CustomerAddress> = ArrayList()
    private val ownedProducts: ArrayList<Product> = ArrayList()

    fun addCustomerAddress(a: CustomerAddress) {
        addresses.add(a)
    }

    fun addOwnedProduct(product: Product) {
        ownedProducts.add(product)
    }

    override fun toString(): String {
        return "CustomerAggregate{" +
                "customer=" + customer +
                ", addresses=" + addresses +
                ", ownedProducts=" + ownedProducts +
                '}'
    }

}