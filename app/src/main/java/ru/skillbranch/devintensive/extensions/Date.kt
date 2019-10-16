package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue
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
fun Date.humanizeDiff(date: Date = Date()): String {
    val timeDiff = date.time - this.time
    val isPast = timeDiff >= 0

    return when (val absoluteDiff = timeDiff.absoluteValue) {
        in 0 until 2 * SECOND -> "только что"

        in 2 * SECOND until 46 * SECOND ->
            if (isPast) "несколько секунд назад" else "через несколько секунд"

        in 46 * SECOND until 76 * SECOND -> if (isPast) "минуту назад" else "через минуту"

        in 76 * SECOND until 46 * MINUTE -> {
            val minutes = (absoluteDiff / MINUTE.toFloat()).roundToInt()
            val plural = TimeUnits.MINUTE.plural(minutes)
            return if (isPast) "$plural назад" else "через $plural"
        }

        in 46 * MINUTE until 76 * MINUTE -> if (isPast) "час назад" else "через час"

        in 76 * MINUTE until 23 * HOUR -> {
            val hours = (absoluteDiff / HOUR.toFloat()).roundToInt()
            val plural = TimeUnits.HOUR.plural(hours)
            return if (isPast) "$plural назад" else "через $plural"
        }

        in 23 * HOUR until 27 * HOUR -> if (isPast) "день назад" else "через день"

        in 27 * HOUR until 361 * DAY.toLong() -> {
            val days = (absoluteDiff / DAY.toFloat()).roundToInt()
            val plural = TimeUnits.DAY.plural(days)
            return if (isPast) "$plural назад" else "через $plural"
        }

        else -> if (isPast) "более года назад" else "более чем через год"
    }
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    val longValue = value.toLong()

    time += when (units) {
        TimeUnits.SECOND -> longValue * SECOND
        TimeUnits.MINUTE -> longValue * MINUTE
        TimeUnits.HOUR -> longValue * HOUR
        TimeUnits.DAY -> longValue * DAY
    }

    this.time = time

    return this

}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int): String = "%s ${when {
            value % 100 in 10..20 -> "секунд"
            value % 10 == 1 -> "секунду"
            value % 10 in 2..4 -> "секунды"
            else -> "секунд"
        }}".format(value)
    },
    MINUTE {
        override fun plural(value: Int): String = "%s ${when {
            value % 100 in 10..20 -> "минут"
            value % 10 == 1 -> "минуту"
            value % 10 in 2..4 -> "минуты"
            else -> "минут"
        }}".format(value)
    },
    HOUR {
        override fun plural(value: Int): String = "%s ${when {
            value % 100 in 10..20 -> "часов"
            value % 10 == 1 -> "час"
            value % 10 in 2..4 -> "часа"
            else -> "часов"
        }}".format(value)
    },
    DAY {
        override fun plural(value: Int): String = "%s ${when {
            value % 100 in 10..20 -> "дней"
            value % 10 == 1 -> "день"
            value % 10 in 2..4 -> "дня"
            else -> "дней"
        }}".format(value)
    };

    abstract fun plural(value: Int): String
}