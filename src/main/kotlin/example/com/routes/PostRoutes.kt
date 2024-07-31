package example.com.routes

import example.com.data.models.Post
import example.com.data.request.CreatePostRequest
import example.com.data.responses.BasicApiResponse
import example.com.data.repository.post.PostRepository
import example.com.service.PostService
import example.com.service.UserService
import example.com.util.ApiResponseMessages
import io.ktor.http.*
import io.ktor.http.ContentType.Message.Http
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
            val email = call.principal<JWTPrincipal>()?.getClaim("email",String::class)
            val isEmailByUser = userService.doesEmailBelongToUserId(email ?: "",request.userId)
            if (!isEmailByUser){
                call.respond(
                    HttpStatusCode.Unauthorized,
                    "you are not who you say you are"
                )
                return@post
            }
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
