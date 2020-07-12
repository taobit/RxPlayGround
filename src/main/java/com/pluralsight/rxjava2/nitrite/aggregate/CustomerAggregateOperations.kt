package com.pluralsight.rxjava2.nitrite.aggregate

import com.pluralsight.rxjava2.nitrite.entity.Customer
import com.pluralsight.rxjava2.nitrite.entity.CustomerAddress
import com.pluralsight.rxjava2.nitrite.entity.Product
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

object CustomerAggregateOperations {
    @JvmStatic
    fun aggregate(customerPartObservable: Observable<Any>): Single<CustomerAggregate> = customerPartObservable.collect({ CustomerAggregate() }
    ) { customerAggregate, nextObject ->
        when (nextObject) {
            is Customer -> customerAggregate.customer = nextObject
            is CustomerAddress -> customerAggregate.addCustomerAddress(nextObject)
            is Product -> customerAggregate.addOwnedProduct(nextObject)
        }
    }
}