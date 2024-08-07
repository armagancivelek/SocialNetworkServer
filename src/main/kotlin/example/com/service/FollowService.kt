package example.com.service

import example.com.data.request.FollowUpdateRequest
import example.com.data.repository.follow.FollowRepository

class FollowService(
    private val followRepository: FollowRepository
) {

    suspend fun followUserIfExist(request : FollowUpdateRequest,followingUserId :String)  = followRepository.followUserIfExists(
        followingUserId,
        request.followedUserId
    )

    suspend fun unFollowUserIfExist(request : FollowUpdateRequest,followingUserId :String)  = followRepository.unfollowUserIfExists(
        followingUserId,
        request.followedUserId
    )
}