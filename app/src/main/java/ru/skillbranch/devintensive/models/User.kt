package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils.parseFullName
import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = Date(),
    var isOnline: Boolean = false
) {

    constructor(id: String, firstName: String?, lastName: String?) : this(
        id,
        firstName,
        lastName,
        null
    )

    companion object Factory {
        private var lastId = -1

        fun makeUser(
            firstName: String? = null,
            lastName: String? = null,
            avatar: String? = null,
            rating: Int = 0,
            respect: Int = 0,
            lastVisit: Date? = Date(),
            isOnline: Boolean = false
        ): User {
            lastId++

            return User(
                "$lastId",
                firstName,
                lastName,
                avatar,
                rating,
                respect,
                lastVisit,
                isOnline
            )
        }

        fun makeUser(fullName: String): User {
            val (firstName, lastName) = parseFullName(fullName)
            return makeUser(
                firstName = firstName,
                lastName = lastName
            )
        }


    }

    data class Builder(
        private var firstName: String? = null,
        private var lastName: String? = null,
        private var avatar: String? = null,
        private var rating: Int = 0,
        private var respect: Int = 0,
        private var lastVisit: Date? = Date(),
        private var isOnline: Boolean = false
    ) {
        fun firstName(firstName: String) = apply { this.firstName = firstName }
        fun lastName(lastName: String) = apply { this.lastName = lastName }
        fun avatar(avatar: String) = apply { this.avatar = avatar }
        fun rating(rating: Int) = apply { this.rating = rating }
        fun respect(respect: Int) = apply { this.respect = respect }
        fun lastVisit(lastVisit: Date) = apply { this.lastVisit = lastVisit }
        fun isOnline(isOnline: Boolean) = apply { this.isOnline = isOnline }
        fun build() = makeUser(
            firstName,
            lastName,
            avatar,
            rating,
            respect,
            lastVisit,
            isOnline
        )
    }

}