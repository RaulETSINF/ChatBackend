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
                email = newUser.email,
                registrationDate = newUser.registrationDate,
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

            }
            put {

            }
            delete {

            }

        }
    }
}