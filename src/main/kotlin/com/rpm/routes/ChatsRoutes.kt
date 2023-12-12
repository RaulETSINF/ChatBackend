package com.rpm.routes

import com.rpm.data.dao.DAOChat
import com.rpm.data.dao.DAOChatImpl
import com.rpm.data.dao.DAOUser
import com.rpm.data.dao.DAOUserImpl
import com.rpm.data.models.Chat
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.chatsRoutes(){

    val chatsDao: DAOChat = DAOChatImpl()

    route("/chats"){

        post {
            val newChat = call.receive<Chat>()
            val addedChat = chatsDao.addNewChat(
                user1Id = newChat.user1Id,
                user2Id = newChat.user2Id,
                lastUpdated = newChat.lastUpdated
            )
            if (addedChat != null){
                call.respond(HttpStatusCode.Created)
            } else {
                call.respondText("Failed to Create a Chat", status = HttpStatusCode.InternalServerError)
            }
        }

        route("/{id?}"){
            delete {

            }

        }

        route("/all/user/{id?}"){
            get {

            }
        }

    }

}