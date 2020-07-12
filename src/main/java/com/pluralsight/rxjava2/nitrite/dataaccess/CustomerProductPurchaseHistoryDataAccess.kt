package com.pluralsight.rxjava2.nitrite.dataaccess

import com.pluralsight.rxjava2.module2.logOnSubscribe
import com.pluralsight.rxjava2.nitrite.entity.CustomerProductPurchaseHistory
import com.pluralsight.rxjava2.nitrite.entity.Product
import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.functions.Supplier
import org.dizitart.no2.Nitrite
import org.dizitart.no2.objects.filters.ObjectFilters
import org.slf4j.LoggerFactory
import java.util.*

object CustomerProductPurchaseHistoryDataAccess {
    private val log = LoggerFactory.getLogger(CustomerProductPurchaseHistoryDataAccess::class.java)
    private fun selectByCustomer(db: Nitrite, customerId: UUID): Observable<CustomerProductPurchaseHistory> {
        return Observable.generate(Supplier<Iterator<CustomerProductPurchaseHistory>> {
            db.getRepository(CustomerProductPurchaseHistory::class.java)
                    .find(ObjectFilters.eq("customerId", customerId))
                    .iterator()
        },
                BiConsumer<Iterator<CustomerProductPurchaseHistory>, Emitter<Any>> { historyIterator: Iterator<CustomerProductPurchaseHistory?>, emitter: Emitter<Any> ->
                    try {
                        if (historyIterator.hasNext()) {
                            val nextHistory = historyIterator.next()
                            log.info("onNext - {}", nextHistory)
                            emitter.onNext(nextHistory)
                        } else {
                            log.info("onComplete")
                            emitter.onComplete()
                        }
                    } catch (t: Throwable) {
                        log.error(t.message, t)
                        emitter.onError(t)
                    }
                }
        )
                .doOnSubscribe { logOnSubscribe() }
                .cast(CustomerProductPurchaseHistory::class.java)
    }

    fun selectOwnedProducts(db: Nitrite, customerId: UUID): Observable<Product> {
        return selectByCustomer(db, customerId) // Map the purchase history into a list of products
                .map { ProductCache.getProduct(db, it.productId) }
    }
}