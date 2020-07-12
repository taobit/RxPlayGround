package com.pluralsight.rxjava2.nitrite.dataaccess

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.nitrite.entity.Customer
import com.pluralsight.rxjava2.nitrite.utility.NitriteFlowableCursorState
import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.functions.Supplier
import org.dizitart.no2.Nitrite
import org.dizitart.no2.objects.filters.ObjectFilters
import java.util.*

object CustomerDataAccess {
    @JvmStatic
    fun select(db: Nitrite, customerId: UUID?): Observable<Customer> {
        return Observable.generate(Supplier<Iterator<Customer>> {
            db.getRepository(Customer::class.java).find(ObjectFilters.eq("customerId", customerId)).iterator()
        }, BiConsumer<Iterator<Customer>, Emitter<Any>> { customers, objectEmitter ->
            try {
                if (customers.hasNext()) {
                    val nextCustomer = customers.next()
                    log.info("onNext - {}", nextCustomer)
                    objectEmitter.onNext(nextCustomer)
                } else {
                    log.info("onComplete")
                    objectEmitter.onComplete()
                }
            } catch (t: Throwable) {
                log.error(t.message, t)
                objectEmitter.onError(t)
            }
        }).doOnSubscribe {
            log.info("onSubscribe")
        }.cast(Customer::class.java)
    }

    fun select(db: Nitrite): Flowable<Customer> {
        return Flowable.generate(
                Supplier<NitriteFlowableCursorState<Customer>> {
                    NitriteFlowableCursorState<Customer>(db.getRepository(Customer::class.java).find())
                },
                BiConsumer { state, customerEmitter ->
                    try {
                        val customerIterator: Iterator<Customer> = state.iterator
                        if (!customerIterator.hasNext()) {
                            customerEmitter.onComplete()
                        } else {
                            customerEmitter.onNext(customerIterator.next())
                        }
                    } catch (t: Throwable) {
                        customerEmitter.onError(t)
                    }
                }
        )
    }
}