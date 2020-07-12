package com.pluralsight.rxjava2.nitrite.dataaccess

import com.pluralsight.rxjava2.nitrite.entity.Product
import org.dizitart.no2.Nitrite
import java.util.*

object ProductCache {
    private val cacheData = HashMap<UUID?, Product>()
    private val cacheLoadLock = Any()
    private fun initialize(db: Nitrite) {
        synchronized(cacheLoadLock) {
            if (cacheData.size == 0) {
                ProductDataAccess.select(db)
                        .subscribe {
                            it?.let {
                                cacheData[it.productId] = it
                            }
                        }
            }
        }
    }

    fun getProduct(db: Nitrite, productId: UUID?): Product? {
        initialize(db)
        return cacheData[productId]
    }
}