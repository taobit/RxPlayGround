package com.pluralsight.rxjava2.nitrite

import org.dizitart.no2.Nitrite

object NO2 {

    fun <T> execute(database: Nitrite, result: ApplyNitriteWorkResult<T>): T {
        return try {
            result.invoke(database)
        } catch (e: Exception) {
            throw RuntimeException(e)
        } finally {
            database.commit()
        }
    }

    fun execute(database: Nitrite, unitOfWork: NitriteUnitOfWork) {
        try {
            unitOfWork.apply(database)
        } catch (e: Exception) {
            throw RuntimeException(e)
        } finally {
            database.commit()
        }
    }
}

typealias ApplyNitriteWorkResult<T> = (Nitrite?) -> T