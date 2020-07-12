package com.pluralsight.rxjava2.module6

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.nitrite.NitriteTestDatabase
import com.pluralsight.rxjava2.nitrite.aggregate.CustomerAggregateOperations.aggregate
import com.pluralsight.rxjava2.nitrite.dataaccess.CustomerAddressDataAccess
import com.pluralsight.rxjava2.nitrite.dataaccess.CustomerDataAccess
import com.pluralsight.rxjava2.nitrite.dataaccess.CustomerProductPurchaseHistoryDataAccess
import com.pluralsight.rxjava2.nitrite.datasets.NitriteCustomerDatabaseSchema
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.system.exitProcess

fun main() {
    try {
        val schema = NitriteCustomerDatabaseSchema()
        NitriteTestDatabase(schema).use { testDatabase ->

            // Create an Observable of Observable<Object>.  Each entry will be one of the
            // Observables for our database that we want to run concurrently.
            val ioFetchStreams = Observable.fromArray( // Fetch the customer information
                    CustomerDataAccess.select(testDatabase.database, schema.Customer1UUID)
                            .subscribeOn(Schedulers.io()).cast(Any::class.java),  // Fetch any address information associated with the customer.
                    CustomerAddressDataAccess.select(testDatabase.database, schema.Customer1UUID)
                            .subscribeOn(Schedulers.io()).cast(Any::class.java),  // Obtain the customer's product history.
                    schema.Customer1UUID?.let {
                        CustomerProductPurchaseHistoryDataAccess.selectOwnedProducts(testDatabase.database,
                                it)
                                .subscribeOn(Schedulers.io()).cast(Any::class.java)
                    }
            )

            // This time we are going to use flatMap to perform the concurrent processing.
            // We restrict the amount of concurrency to 2 simultaneous
            // subscriptions.  We just want to pass through the Observable<Object>, so
            // we use the RxJava 2 built-in identity function.
            val customerAggregateStream: Observable<Any> = ioFetchStreams.flatMap(
                    Functions.identity()
            )

            // Let's assemble a CustomerAggregate.
            val customerAggregate = aggregate(customerAggregateStream)

            // Get the aggregated customer data!
            val finalCustomer = customerAggregate.blockingGet()
            log.info(finalCustomer.toString())
        }
    } catch (t: Throwable) {
        log.error(t.message, t)
    }
    exitProcess(0)
}
