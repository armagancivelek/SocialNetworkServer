package example.com.data.repository.user

import example.com.data.models.User
import example.com.data.request.UpdateProfileRequest

interface UserRepository {

    suspend fun createUser(user: User)
    suspend fun getByUserId(id : String) : User?
    suspend fun getUserByEmail(email : String) : User?

    suspend fun getUserById(id: String): User?


    suspend fun updateUser(
        userId: String,
        profileImageUrl: String?,
        bannerUrl: String?,
        updateProfileRequest: UpdateProfileRequest
    ): Boolean

    suspend fun searchForUsers(query: String): List<User>

    suspend fun doesPasswordForUserMatch(email: String, enteredPassword: String): Boolean

    suspend fun getUsers(userIds: List<String>): List<User>
    suspend fun doesEmailBelongToUserId(email : String, userId: String) : Boolean
}

