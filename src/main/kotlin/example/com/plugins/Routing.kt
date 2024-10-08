package example.com.plugins

import example.com.routes.*
import example.com.service.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userService: UserService by inject()
    val postService: PostService by inject()
    val followService: FollowService by inject()
    val likeService: LikeService by inject()
    val commentService: CommentService by inject()
    val activityService: ActivityService by inject()

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
        searchUser(userService)
        getUserProfile(userService)
        getPostsForProfile(postService)
        updateUserProfile(userService)

        // Following
        followUser(followService, activityService)
        unfollowUser(followService)

        // post routes
        createPostRoutes(postService)
        getPostsForFollows(postService)
        deletePost(postService,likeService,commentService)

        // like routes
        likeParent(likeService,activityService)
        unlikeParent(likeService)
        getLikesForParent(likeService)

        // Comment routes
        createComment(commentService,activityService)
        deleteComment(commentService,likeService)
        getCommentsForPost(commentService)

        //Activity Routes
        getActivities(activityService)

    }
}
