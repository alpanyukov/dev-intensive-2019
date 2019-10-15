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
    val isPast = timeDiff > 0

    return when (val absoluteDiff = timeDiff.absoluteValue) {
        in 0 until SECOND -> "только что"

        in SECOND until 45 * SECOND ->
            if (isPast) "несколько секунд назад" else "через несколько секунд"

        in 45 * SECOND until 75 * SECOND -> if (isPast) "минуту назад" else "через минуту"

        in 75 * SECOND until 45 * MINUTE -> {
            val minutes = (absoluteDiff / MINUTE.toFloat()).roundToInt()
            val plural = TimeUnits.MINUTE.plural(minutes)
            return if (isPast) "$minutes $plural назад" else "через $minutes $plural"
        }

        in 45 * MINUTE until 75 * MINUTE -> if(isPast) "час назад" else "через час"

        in 75 * MINUTE until 22 * HOUR -> {
            val hours = (absoluteDiff / HOUR.toFloat()).roundToInt()
            val plural = TimeUnits.HOUR.plural(hours)
            return if (isPast) "$hours $plural назад" else "через $hours $plural"
        }

        in 22 * HOUR until 26 * HOUR -> if(isPast) "день назад" else "через день"

        in 26 * HOUR until 360 * DAY.toLong() -> {
            val days = (absoluteDiff / DAY.toFloat()).roundToInt()
            val plural =TimeUnits.DAY.plural(days)
            return if(isPast) "$days $plural назад" else "через $days $plural"
        }

        else -> if(isPast) "более года назад" else "более чем через год"
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
        override fun plural(value: Int): String = when (value % 10) {
            1 -> "секунда"
            in 2..4 -> "секунды"
            else -> "cекунд"
        }
    },
    MINUTE {
        override fun plural(value: Int): String = when (value % 10) {
            1 -> "минута"
            in 2..4 -> "минуты"
            else -> "минут"
        }
    },
    HOUR {
        override fun plural(value: Int): String = when (value % 10) {
            1 -> "час"
            in 2..4 -> "часа"
            else -> "часов"
        }
    },
    DAY {
        override fun plural(value: Int): String = when (value % 10) {
            1 -> "день"
            in 2..4 -> "дня"
            else -> "дней"
        }
    };

    abstract fun plural(value: Int): String
}