package com.rpm.routes

import com.rpm.data.dao.DAOUser
import com.rpm.data.dao.DAOUserImpl
import com.rpm.data.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes() {

    val userDao: DAOUser = DAOUserImpl()

    route("/users") {

        get {
            val users = userDao.allUsers()
            if (users.isNotEmpty()) {
                call.respond(users)
            } else {
                call.respondText("No Users Found", status = HttpStatusCode.OK)
            }
        }

        post {
            val newUser = call.receive<User>()
            val addedUser = userDao.addNewUser(
                username = newUser.username,
                password = newUser.password,
                fullName = newUser.fullName,
                email = newUser.email,
                gender = newUser.gender
            )
            if (addedUser != null) {
                call.respond(HttpStatusCode.Created, addedUser)
            } else {
                call.respondText("Failed to add user", status = HttpStatusCode.InternalServerError)
            }
        }

        route("/{id?}") {
            get {
                val userId = call.parameters["id"]?.toIntOrNull() ?: return@get call.respondText(
                    "Missing user ID",
                    status = HttpStatusCode.BadRequest
                )

                val user = userDao.user(userId)
                if (user != null) {
                    call.respond(user)
                } else {
                    call.respond(HttpStatusCode.NotFound, "User not found")
                }
            }
            put {
                val userId = call.parameters["id"]?.toIntOrNull() ?: return@put call.respondText(
                    "Missing user ID",
                    status = HttpStatusCode.BadRequest
                )
                val user = userDao.user(userId)
                if (user != null){
                    val updatedEmail = call.receive<Map<String, String>>()["email"]
                    if (!updatedEmail.isNullOrEmpty()){
                        if (userDao.editUser(userId, updatedEmail)){
                            call.respond(HttpStatusCode.OK, "User updated")
                        } else {
                            call.respond(HttpStatusCode.NotModified, "User not updated")
                        }
                    } else {
                        call.respond(HttpStatusCode.NotFound, "email not provided")
                    }
                } else {
                    call.respond(HttpStatusCode.NotFound, "User not found")
                }
            }
            delete {
                val userId = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respondText(
                    "Missing user ID",
                    status = HttpStatusCode.BadRequest
                )
                if (userDao.deleteUser(userId)){
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound, "User not found")
                }
            }
        }
    }
}