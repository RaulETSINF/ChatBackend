package com.rpm.data.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val registrationDate: String,
    val gender: Gender?
)

object Users: IntIdTable(){
    val username = varchar("username", 50).uniqueIndex()
    val password = varchar("password", 100)
    val email = varchar("email", 100)
    val registrationDate = varchar("registration_date", 100)
    val gender = enumerationByName("gender", 10, Gender::class).nullable()
}
