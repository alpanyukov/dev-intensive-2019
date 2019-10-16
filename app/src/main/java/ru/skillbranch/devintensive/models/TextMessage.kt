package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.humanizeDiff
import java.util.*

class TextMessage(
    id: String,
    from: User?,
    chat: Chat,
    isIncoming: Boolean = false,
    date: Date = Date(),
    var text: String?
) : BaseMessage(id, from, chat, isIncoming, date) {

    override fun formatMessage(): String {
        val message = text?.trim()
        if (message == "" || message == null) {
            return ""
        }

        val userName = from?.firstName ?: "Аноним"
        val dateDiff = date.humanizeDiff()
        val directionText = if (isIncoming) "получил" else "отправил"

        return "$userName $directionText сообщение \"$message\" $dateDiff"
    }

}