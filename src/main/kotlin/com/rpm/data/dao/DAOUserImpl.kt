package com.rpm.data.dao

import com.rpm.data.models.Gender
import com.rpm.data.models.User
import com.rpm.data.models.Users
import com.rpm.plugins.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOUserImpl : DAOUser {

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id].value,
        username = row[Users.username],
        password = row[Users.password],
        email = row[Users.email],
        registrationDate = row[Users.registrationDate],
        gender = row[Users.gender]
    )

    override suspend fun allUsers(): List<User> = dbQuery {
        Users.selectAll().orderBy(Users.id).map(::resultRowToUser)
    }

    override suspend fun user(id: Int): User? = dbQuery {
        Users.select { Users.id eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun addNewUser(
        username: String,
        password: String,
        email: String,
        registrationDate: String,
        gender: Gender?
    ): User? = dbQuery {
        val insertedStatement = Users.insert {
            it[Users.username] = username
            it[Users.password] = password
            it[Users.email] = email
            it[Users.registrationDate] = registrationDate
            it[Users.gender] = gender
        }
        insertedStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun editUser(id: Int, email: String): Boolean = dbQuery {
        Users.update({Users.id eq id }){
            it[Users.email] = email
        } > 0
    }

    override suspend fun deleteUser(id: Int): Boolean = dbQuery{
        Users.deleteWhere { Users.id eq id } > 0
    }

}