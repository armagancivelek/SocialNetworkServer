package example.com.data.repository.user

import example.com.data.models.User

interface UserRepository {

    suspend fun createUser(user: User)
    suspend fun getByUserId(id : String) : User?
    suspend fun getUserByEmail(email : String) : User?

    suspend fun searchForUsers(query: String): List<User>

    suspend fun doesPasswordForUserMatch(email: String, enteredPassword: String): Boolean

    suspend fun doesEmailBelongToUserId(email : String, userId: String) : Boolean
}

