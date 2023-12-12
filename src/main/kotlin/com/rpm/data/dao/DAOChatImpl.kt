package com.rpm.data.dao

import com.rpm.data.models.Chat
import com.rpm.data.models.Chats
import com.rpm.data.models.Users
import com.rpm.plugins.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOChatImpl : DAOChat {

    private fun resultRowToChat(row: ResultRow) = Chat(
        id = row[Chats.id].value,
        user1Id = row[Chats.user1Id],
        user2Id = row[Chats.user2Id],
        lastUpdated = row[Chats.lastUpdated]
    )

    override suspend fun allChatsFromUser(idUser: Int): List<Chat> = dbQuery {
        Chats.select { (Chats.user1Id eq idUser) or (Chats.user2Id eq idUser) }.map(::resultRowToChat)
    }

    override suspend fun addNewChat(user1Id: Int, user2Id: Int, lastUpdated: String): Chat? = dbQuery {
        val insertedStatement = Chats.insert {
            it[Chats.user1Id] = user1Id
            it[Chats.user2Id] = user2Id
            it[Chats.lastUpdated] = lastUpdated
        }
        insertedStatement.resultedValues?.singleOrNull()?.let(::resultRowToChat)
    }

    override suspend fun chat(id: Int): Chat? = dbQuery {
        Chats.select{Chats.id eq id}
            .map(::resultRowToChat)
            .singleOrNull()
    }

    override suspend fun deleteChat(id: Int): Boolean  = dbQuery{
        Chats.deleteWhere { Chats.id eq id } > 0
    }

}