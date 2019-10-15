package ru.skillbranch.devintensive.extensions

fun String.truncate(maxLength: Int = 16): String {
    val result = this.trim()

    if (maxLength >= result.length) {
        return result
    }

    return result.filterIndexed { index, _ -> index < maxLength }.trim() + "..."
}

fun String.stripHtml(): String {
    return this.trim()
        .replace(Regex("(<[^>]+>)"), "")
        .replace(Regex("&.+;"), "")
        .replace(Regex(" +"), " ")
}