package com.pluralsight.rxjava2.nitrite

import org.dizitart.no2.Nitrite

object NO2 {
    @JvmStatic
    fun <T> execute(database: Nitrite, unitOfWork: NitriteUnitOfWorkWithResult<T>): T {
        return try {
            unitOfWork.apply(database)
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