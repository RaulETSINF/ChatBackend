package com.rpm.data.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption


@Serializable
data class Message(
    val id: Int,
    val chatId: Int,
    val senderId: Int,
    val content: String,
    val timestamp: String
)

object Messages: IntIdTable() {
    val chatId = reference("chat_id", Chats.id, onDelete = ReferenceOption.CASCADE)
    val senderId = reference("sender_id", Users.id, onDelete = ReferenceOption.CASCADE)
    val content = text("content")
    val timestamp = varchar("timestamp", 100)
}
