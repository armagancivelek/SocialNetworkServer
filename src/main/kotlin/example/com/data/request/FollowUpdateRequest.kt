package example.com.data.request

data class FollowUpdateRequest(
    val followingUserId : String,
    val followedUserId : String
)