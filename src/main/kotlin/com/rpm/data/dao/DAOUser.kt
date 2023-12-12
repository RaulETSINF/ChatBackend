package com.rpm.data.dao

import com.rpm.data.models.Gender
import com.rpm.data.models.User

interface DAOUser {
    suspend fun allUsers():List<User>
    suspend fun user(id: Int): User?
    suspend fun addNewUser(username: String, password: String, email: String, registrationDate: String, gender: Gender?): User?
    suspend fun editUser(id: Int, email: String): Boolean
    suspend fun deleteUser(id: Int): Boolean
}