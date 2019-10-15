package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?>{
        val parts= fullName?.trim()?.split(" ")

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

    fun toInitials(firstName: String?, lastName: String?): String?{
        val firstNameInitial = takeInitialLetter(firstName)
        val lastNameInitial = takeInitialLetter(lastName)

        val initials = firstNameInitial + lastNameInitial

        return if (initials == "") null else initials
    }
}