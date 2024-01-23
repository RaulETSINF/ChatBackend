package com.rpm.data.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class User(
    val id: Int = 0,
    val username: String,
    val password: String,
    val fullName: String,
    val email: String,
    val registrationDate: String = "",
    val gender: Gender?
)

object Users: IntIdTable(){
    val username = varchar("username", 50).uniqueIndex()
    val password = varchar("password", 100)
    val fullName = varchar("full_name", 100)
    val email = varchar("email", 100)
    val registrationDate = varchar("registration_date", 25)
    val gender = enumerationByName("gender", 10, Gender::class).nullable()
}
