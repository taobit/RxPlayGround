package com.pluralsight.rxjava2.nitrite.datasets

import com.pluralsight.rxjava2.nitrite.NitriteSchema
import com.pluralsight.rxjava2.nitrite.entity.LetterPair
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet.greekAlphabetInEnglishObservable
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet.greekAlphabetInGreekObservable
import io.reactivex.rxjava3.kotlin.Observables
import org.dizitart.no2.Nitrite

class NitriteGreekAlphabetSchema : NitriteSchema {
    override fun applySchema(db: Nitrite) {

        // Make a collection to hold the greek alphabet
        db.getRepository(LetterPair::class.java)?.let {

            // See if it's already populated
            if (it.find()?.totalCount() == 0) {

                // Make a LetterPair for each letter in the greek alphabet
                val letterList = Observables.zip(
                        greekAlphabetInGreekObservable(),
                        greekAlphabetInEnglishObservable())
                { greekLetter, englishRepresentation -> LetterPair(greekLetter, englishRepresentation) }
                        .toList()
                        .blockingGet()
                it.insert(letterList.toTypedArray())
            }
        }
    }
}