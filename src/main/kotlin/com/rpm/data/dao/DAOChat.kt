package com.rpm.data.dao

import com.rpm.data.models.Chat

interface DAOChat {
    suspend fun allChatsFromUser(idUser: Int): List<Chat>
    suspend fun addNewChat(user1Id: Int, user2Id: Int, lastUpdated: String): Chat?
    suspend fun chat(id: Int): Chat?
    suspend fun deleteChat(id: Int): Boolean
}