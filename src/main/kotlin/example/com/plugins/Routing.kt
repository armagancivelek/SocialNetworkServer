package example.com.plugins

import example.com.routes.*
import example.com.service.FollowService
import example.com.service.PostService
import example.com.service.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userService: UserService by inject()
    val postService: PostService by inject()
    val followService: FollowService by inject()

    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()
    routing {
        // User Routes
        createUserRoute(userService)
        loginUser(
            userService = userService,
            jwtIssuer = jwtIssuer,
            jwtSecret = jwtSecret,
            jwtAudience = jwtAudience
        )

        // Following
        followUser(followService)
        unfollowUser(followService)

        // post routes
        createPostRoutes(postService,userService)
        getPostsForFollows(postService,userService)
    }
}
