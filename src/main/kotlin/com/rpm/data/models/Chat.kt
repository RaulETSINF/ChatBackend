package com.rpm.data.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

@Serializable
data class Chat(
    val id: Int,
    val user1Id: Int,
    val user2Id: Int,
    val lastUpdated: String
)

object Chats : IntIdTable() {
    val user1Id = integer("user1_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val user2Id = integer("user2_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val lastUpdated = varchar("lastUpdated", 100)
}
