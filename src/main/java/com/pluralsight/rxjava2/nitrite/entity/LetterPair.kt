package com.pluralsight.rxjava2.nitrite.entity

data class LetterPair(var greekLetter: String?, var englishRepresentation: String?) {

    override fun toString(): String {
        return "LetterPair{" +
                "greekLetter='" + greekLetter + '\'' +
                ", englishRepresentation='" + englishRepresentation + '\'' +
                '}'
    }
}