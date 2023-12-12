package com.rpm.plugins

import com.rpm.data.models.Chats
import com.rpm.data.models.Messages
import com.rpm.data.models.Users
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/chat_lab",
        user = "postgres",
        driver = "org.postgresql.Driver",
        password = "Raulet2000"
    )

    transaction(database) {
        SchemaUtils.create(Users)
        SchemaUtils.create(Chats)
        SchemaUtils.create(Messages)
    }
}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }
