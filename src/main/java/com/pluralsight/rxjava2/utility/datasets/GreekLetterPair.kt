package com.pluralsight.rxjava2.utility.datasets

data class GreekLetterPair(val greekLetter: String, val englishLetter: String) {

    override fun toString() =
            "GreekLetterPair{" +
                    "greekLetter='" + greekLetter + '\'' +
                    ", englishLetter='" + englishLetter + '\'' +
                    '}'

}
