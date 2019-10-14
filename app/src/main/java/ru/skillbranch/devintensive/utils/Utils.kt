package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?>{
        val parts= fullName?.trim()?.split(" ")

        var firstName = parts?.getOrNull(0)
        firstName = if (firstName == "") null else firstName

        var lastName = parts?.getOrNull(1)
        lastName = if (lastName == "") null else lastName

        return firstName to lastName
    }
}