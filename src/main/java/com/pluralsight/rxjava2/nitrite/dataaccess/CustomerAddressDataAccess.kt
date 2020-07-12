package com.pluralsight.rxjava2.nitrite.dataaccess

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.nitrite.entity.CustomerAddress
import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.functions.Supplier
import org.dizitart.no2.Nitrite
import org.dizitart.no2.objects.filters.ObjectFilters
import java.util.*

object CustomerAddressDataAccess {

    @JvmStatic
    fun select(db: Nitrite, customerId: UUID?): Observable<CustomerAddress> {
        return Observable.generate(Supplier<Iterator<CustomerAddress>> {
            db.getRepository(CustomerAddress::class.java)
                    .find(ObjectFilters.eq("customerId", customerId))
                    .iterator()
        },
                BiConsumer<Iterator<CustomerAddress>, Emitter<Any>> { customerAddressIterator, emitter ->
                    try {
                        if (customerAddressIterator.hasNext()) {
                            val nextAddress = customerAddressIterator.next()

                            log.info("onNext - {}", nextAddress)
                            emitter.onNext(nextAddress)
                        } else {
                            log.info("onComplete")
                            emitter.onComplete()
                        }
                    } catch (t: Throwable) {
                        log.error(t.message, t)
                        emitter.onError(t)
                    }
                }
        ).doOnSubscribe { log.info("onSubscribe") }
                .cast(CustomerAddress::class.java)
    }
}
