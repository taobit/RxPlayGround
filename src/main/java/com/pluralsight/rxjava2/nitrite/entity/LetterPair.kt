package com.pluralsight.rxjava2.nitrite.entity

data class LetterPair(var greekLetter: String? = null, var englishRepresentation: String? = null) {

    override fun toString() = "LetterPair{" +
            "greekLetter='" + greekLetter + '\'' +
            ", englishRepresentation='" + englishRepresentation + '\'' +
            '}'
}