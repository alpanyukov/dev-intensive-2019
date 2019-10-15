package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

const val SECOND = 1000
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

/**
    Преобразует по принципу
    0с - 1с "только что"
    1с - 45с "несколько секунд назад"
    45с - 75с "минуту назад"
    75с - 45мин "N минут назад"
    45мин - 75мин "час назад"
    75мин 22ч "N часов назад"
    22ч - 26ч "день назад"
    26ч - 360д "N дней назад"
    >360д "более года назад"
 */
fun Date.humanizeDiff(date: Date = Date()): String{
    val timeDiff = this.time - date.time

    return when(timeDiff){
        in 0 until SECOND -> "только что"                                                                   // 0с - 1с "только что"
        in SECOND until 45 * SECOND -> "несколько секунд назад"                                             // 1с - 45с "несколько секунд назад" "через несколько секунд"
        in 45 * SECOND until 75 * SECOND -> "минуту назад"                                                  // 45с - 75с "минуту назад" "через минуту"
        in 75 * SECOND until 45 * MINUTE -> "${(timeDiff / MINUTE.toFloat()).roundToInt()} минут назад"     // 75с - 45мин "N минут назад" "через "
        in 45 * MINUTE until 75 * MINUTE -> "час назад"                                                     // 45мин - 75мин "час назад"
        in 75 * MINUTE until 22 * HOUR -> "${(timeDiff / HOUR.toFloat()).roundToInt()} часов назад"         // 75мин 22ч "N часов назад"
        in 22 * HOUR until 26 * HOUR -> "день назад"                                                        // 22ч - 26ч "день назад"
        in 26 * HOUR until 360 * DAY.toLong() -> "${(timeDiff / DAY.toFloat()).roundToInt()} дней назад"    // 26ч - 360д "N дней назад"
        else -> "более года назад"
    }
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    val longValue = value.toLong()

    time += when(units){
        TimeUnits.SECOND -> longValue * SECOND
        TimeUnits.MINUTE -> longValue * MINUTE
        TimeUnits.HOUR -> longValue * HOUR
        TimeUnits.DAY -> longValue * DAY
    }

    this.time = time

    return this

}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}