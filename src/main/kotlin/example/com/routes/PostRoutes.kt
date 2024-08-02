package example.com.routes

import example.com.data.request.CreatePostRequest
import example.com.data.request.DeletePostRequest
import example.com.data.responses.BasicApiResponse
import example.com.plugins.email
import example.com.service.LikeService
import example.com.service.PostService
import example.com.service.UserService
import example.com.util.ApiResponseMessages
import example.com.util.Constants
import example.com.util.QueryParams
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createPostRoutes(
    postService: PostService,
    userService: UserService
) {
    authenticate {
        post("/api/post/create") {
            val request = call.receiveNullable<CreatePostRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            ifEmailBelongsToUser(
                userId = request.userId,
                validateEmail = userService::doesEmailBelongToUserId
            ) {
                if (!postService.createPostIfUserExist(request)) {
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(
                            successful = false,
                            message = ApiResponseMessages.USER_NOT_FOUND
                        )
                    )
                } else {
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(
                            successful = true,
                        )
                    )
                }
            }
        }
    }
}

fun Route.getPostsForFollows(
    postService: PostService,
    userService: UserService
) {
    authenticate {
        get("/api/post/get") {
            val userId = call.parameters[QueryParams.PARAM_USER_ID] ?: kotlin.run {
                call.respond(
                    HttpStatusCode.BadRequest
                )
                return@get
            }

            val page = call.parameters[QueryParams.PARAM_PAGE]?.toIntOrNull() ?: 0
            val pageSize =
                call.parameters[QueryParams.PARAM_PAGE_SIZE]?.toIntOrNull() ?: Constants.DEFAULT_POST_PAGE_SIZE

            ifEmailBelongsToUser(
                userId = userId,
                validateEmail = userService::doesEmailBelongToUserId
            ) {
                val posts = postService.getPostForFollows(userId, page, pageSize)
                call.respond(
                    HttpStatusCode.OK,
                    posts
                )
            }
        }

    }
}

 fun Route.deletePost(
    postService: PostService,
    userService: UserService,
    likeService: LikeService
) {
    delete("/api/post/delete") {
        val request = call.receiveNullable<DeletePostRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }
          val post = postService.getPost(request.postId)
        if (post == null) {
            call.respond(
                HttpStatusCode.NotFound
            )
            return@delete
        }
         ifEmailBelongsToUser(
             userId = post.userId,
             validateEmail = userService::doesEmailBelongToUserId
         ) {
             postService.deletePost(request.postId)
             likeService.deleteLikesForParent(request.postId)
             // TODO: Delete comments from post
             call.respond(HttpStatusCode.OK)
         }

    }
}