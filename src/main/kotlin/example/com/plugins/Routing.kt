package example.com.plugins

import example.com.repository.follow.FollowRepository
import example.com.repository.post.PostRepository
import example.com.repository.user.UserRepository
import example.com.repository.user.UserRepositoryImp
import example.com.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userRepository : UserRepository by inject()
    val followRepository : FollowRepository by inject()
    val postRepository : PostRepository by inject()
    routing {
        // User Routes
      createUserRoute(userRepository)
        loginUser(userRepository)

        // Following
        followUser(followRepository)
        unfollowUser(followRepository)
        createPostRoutes(postRepository)
    }
}
