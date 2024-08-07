package example.com.routes

import example.com.data.request.LikeUpdateRequest
import example.com.data.responses.BasicApiResponse
import example.com.service.LikeService
import example.com.util.ApiResponseMessages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.likeParent(
    likeService: LikeService,
) {
    authenticate {
        post("/api/like") {
            val request = call.receiveNullable<LikeUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val likeSuccessful = likeService.likeParent(call.userId,request.parentId)
            if(likeSuccessful) {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(
                        successful = true
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(
                        successful = false,
                        message = ApiResponseMessages.USER_NOT_FOUND
                    )
                )

            }

        }
    }
}

fun Route.unlikeParent(
    likeService: LikeService,
) {
    authenticate {
        delete("/api/unlike") {
            val request = call.receiveNullable<LikeUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val unlikeSuccessful = likeService.likeParent(call.userId,request.parentId)
            if(unlikeSuccessful) {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(
                        successful = true
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(
                        successful = false,
                        message = ApiResponseMessages.USER_NOT_FOUND
                    )
                )

            }

        }
    }
}