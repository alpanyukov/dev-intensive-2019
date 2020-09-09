package ru.skillbranch.devintensive.repositories

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.models.Profile

object PreferencesRepository {
    private const val FIRST_NAME = "FIRST_NAME"
    private const val LAST_NAME = "LAST_NAME"
    private const val ABOUT = "ABOUT"
    private const val REPOSITORY = "REPOSITORY"
    private const val RATING = "RATING"
    private const val RESPECT = "RESPECT"
    private const val APP_THEME = "APP_THEME"

    private val pref: SharedPreferences by lazy {
        val ctx = App.applicationContext()
        PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun getProfile(): Profile = Profile(
            firstName = pref.getString(FIRST_NAME, "")!!,
            lastName = pref.getString(LAST_NAME, "")!!,
            about = pref.getString(ABOUT, "")!!,
            repository = pref.getString(REPOSITORY, "")!!,
            rating = pref.getInt(RATING, 0),
            respect = pref.getInt(RESPECT, 0)
    )

    fun saveProfile(profile: Profile) {
        with(profile){
            putValue(FIRST_NAME to firstName)
            putValue(LAST_NAME to lastName)
            putValue(ABOUT to about)
            putValue(REPOSITORY to repository)
            putValue(RATING to rating)
            putValue(RESPECT to respect)
        }
    }

    fun saveAppTheme(value: Int) {
        putValue(APP_THEME to value)
    }

    fun getAppTheme() = pref.getInt(APP_THEME, AppCompatDelegate.MODE_NIGHT_NO)

    private fun putValue(pair: Pair<String, Any>) = with(pref.edit()){
        val (key, value) = pair

        when(value){
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitives")
        }
        apply()
    }


}