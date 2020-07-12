package com.pluralsight.rxjava2.module6

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.module2.logError
import com.pluralsight.rxjava2.nitrite.NitriteTestDatabase
import com.pluralsight.rxjava2.nitrite.aggregate.CustomerAggregateOperations
import com.pluralsight.rxjava2.nitrite.dataaccess.CustomerAddressDataAccess
import com.pluralsight.rxjava2.nitrite.dataaccess.CustomerDataAccess
import com.pluralsight.rxjava2.nitrite.dataaccess.CustomerProductPurchaseHistoryDataAccess
import com.pluralsight.rxjava2.nitrite.datasets.NitriteCustomerDatabaseSchema
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

fun main() {
    try {
        val schema = NitriteCustomerDatabaseSchema()
        val database = NitriteTestDatabase(schema)
        val dataObservable = CustomerDataAccess.select(database.database, schema.Customer1UUID)
                .subscribeOn(Schedulers.io())
        val addressObservable = CustomerAddressDataAccess.select(database.database, schema.Customer1UUID)
                .subscribeOn(Schedulers.io())
        val productObservable = schema.Customer1UUID?.let {
            CustomerProductPurchaseHistoryDataAccess.selectOwnedProducts(database.database, it)
                    .subscribeOn(Schedulers.io())
        }
        val stream = Observable.concat(
                dataObservable.cast(Any::class.java),
                addressObservable.cast(Any::class.java),
                productObservable?.cast(Any::class.java)
        )
        log.info(CustomerAggregateOperations.aggregate(stream).blockingGet().toString())
    } catch (t: Throwable) {
        logError(t)
    }
}