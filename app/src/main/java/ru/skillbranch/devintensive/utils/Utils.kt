package ru.skillbranch.devintensive.utils

import android.annotation.SuppressLint
import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts = fullName?.trim()?.split(" ")

        var firstName = parts?.getOrNull(0)
        firstName = if (firstName == "") null else firstName

        var lastName = parts?.getOrNull(1)
        lastName = if (lastName == "") null else lastName

        return firstName to lastName
    }

    private fun takeInitialLetter(word: String?): String {
        val trimmed = word?.trim() ?: ""
        return trimmed.toUpperCase(Locale("ru")).take(1)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstNameInitial = takeInitialLetter(firstName)
        val lastNameInitial = takeInitialLetter(lastName)

        val initials = firstNameInitial + lastNameInitial

        return if (initials == "") null else initials
    }


    @SuppressLint("DefaultLocale")
    fun transliteration(payload: String, divider: String = " "): String {

        return payload.trim().replace(" ", divider).split("").map {
            val lower = it.toLowerCase(Locale("ru"))
            val isCapitalized = lower != it
            val translated = translateDictionary.getOrElse(lower, { it })
            if (isCapitalized) translated.capitalize() else translated
        }.joinToString("")
    }
}

private val translateDictionary: Map<String, String> = mapOf(
    Pair("а", "a"), Pair("б", "b"), Pair("в", "v"),
    Pair("г", "g"), Pair("д", "d"), Pair("е", "e"),
    Pair("ё", "e"), Pair("ж", "zh"), Pair("з", "z"),
    Pair("и", "i"), Pair("й", "i"), Pair("к", "k"),
    Pair("л", "l"), Pair("м", "m"), Pair("н", "n"),
    Pair("о", "o"), Pair("п", "p"), Pair("р", "r"),
    Pair("с", "s"), Pair("т", "t"), Pair("у", "u"),
    Pair("ф", "f"), Pair("х", "h"), Pair("ц", "c"),
    Pair("ч", "ch"), Pair("ш", "sh"), Pair("щ", "sh"),
    Pair("ъ", ""), Pair("ы", "i"), Pair("ь", ""),
    Pair("э", "e"), Pair("ю", "yu"), Pair("я", "ya")
)