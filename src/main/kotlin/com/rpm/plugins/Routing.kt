package com.rpm.plugins

import com.rpm.routes.chatsRoutes
import com.rpm.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        userRoutes()
        chatsRoutes()
    }
}
