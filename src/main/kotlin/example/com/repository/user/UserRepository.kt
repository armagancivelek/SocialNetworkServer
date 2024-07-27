package example.com.repository.user

import example.com.data.models.User

interface UserRepository {

    suspend fun createUser(user: User)
    suspend fun getByUserId(id : String) : User?
    suspend fun getUserByEmail(email : String) : User?
}