package com.pluralsight.rxjava2.nitrite.dataaccess

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.nitrite.entity.Product
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import org.dizitart.no2.Nitrite

object ProductDataAccess {
    fun select(db: Nitrite): Observable<Product?> {
        return Observable.create { emitter: ObservableEmitter<Product?> ->
            try {
                for (nextProduct in db.getRepository(Product::class.java)
                        .find()) {
                    log.info(nextProduct.toString());
                    emitter.onNext(nextProduct)
                }
                emitter.onComplete()
            } catch (t: Throwable) {
                emitter.onError(t)
            }
        }
    }
}