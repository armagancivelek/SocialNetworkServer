package example.com.service

import example.com.data.models.User
import example.com.data.repository.follow.FollowRepository
import example.com.data.repository.user.UserRepository
import example.com.data.request.CreateAccountRequest
import example.com.data.request.LoginRequest
import example.com.data.request.UpdateProfileRequest
import example.com.data.responses.ProfileResponse
import example.com.data.responses.UserResponseItem

class UserService(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
) {
    suspend fun doesUserWithEmailExist(email: String): Boolean {
        return userRepository.getUserByEmail(email) != null
    }

    suspend fun doesPasswordMatchForUser(request: LoginRequest) =
        userRepository.doesPasswordForUserMatch(
            enteredPassword = request.password,
            email = request.email
        )

    suspend fun doesEmailBelongToUserId(email: String, userId: String) =
        userRepository.doesEmailBelongToUserId(email, userId)

    suspend fun getUserByEmail(email: String): User? {
        return userRepository.getUserByEmail(email)
    }


    suspend fun updateUser(
        userId: String,
        profileImageUrl: String?,
        bannerUrl: String?,
        updateProfileRequest: UpdateProfileRequest
    ): Boolean {
        return userRepository.updateUser(userId, profileImageUrl, bannerUrl, updateProfileRequest)
    }

    suspend fun getUserProfile(userId: String, callerUserId: String): ProfileResponse? {
        val user = userRepository.getUserById(userId) ?: return null
        return ProfileResponse(
            userId = user.id,
            username = user.username,
            bio = user.bio,
            followerCount = user.followerCount,
            followingCount = user.followingCount,
            postCount = user.postCount,
            profilePictureUrl = user.profileImageUrl,
            bannerUrl = user.bannerUrl,
            topSkills = user.skills,
            gitHubUrl = user.gitHubUrl,
            instagramUrl = user.instagramUrl,
            linkedInUrl = user.linkedInUrl,
            isOwnProfile = userId == callerUserId,
            isFollowing = if (userId != callerUserId) {
                followRepository.doesUserFollow(callerUserId, userId)
            } else {
                false
            }
        )
    }

    fun isValidPassword(enteredPassword: String, actualPassword: String): Boolean {
        return enteredPassword == actualPassword
    }

    suspend fun createUser(request: CreateAccountRequest) {
        userRepository.createUser(
            User(
                email = request.email,
                username = request.username,
                password = request.password,
                profileImageUrl = "",
                bio = "",
                gitHubUrl = null,
                instagramUrl = null,
                linkedInUrl = null,
                bannerUrl = null
            )
        )
    }

    suspend fun searchForUsers(query: String, userId: String): List<UserResponseItem> {
        val users = userRepository.searchForUsers(query)
        val followsByUser = followRepository.getFollowsByUser(userId)
        return users.map { user ->
            val isFollowing = followsByUser.find { it.followedUserId == user.id } != null
            UserResponseItem(
                userId = user.id,
                username = user.username,
                profilePictureUrl = user.profileImageUrl,
                bio = user.bio,
                isFollowing = isFollowing
            )
        }.filter { it.userId != userId }
    }

    fun validateCreateAccountRequest(request: CreateAccountRequest): ValidationEvent {
        if (request.email.isBlank() || request.password.isBlank() || request.username.isBlank()) {
            return ValidationEvent.ErrorFieldEmpty
        }
        return ValidationEvent.Success
    }

    sealed class ValidationEvent {
        data object ErrorFieldEmpty : ValidationEvent()
        data object Success : ValidationEvent()
        data object UserNotFound : ValidationEvent()
    }
}

