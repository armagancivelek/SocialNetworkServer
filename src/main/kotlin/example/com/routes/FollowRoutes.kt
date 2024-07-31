package example.com.routes

import example.com.data.request.FollowUpdateRequest
import example.com.data.responses.BasicApiResponse
import example.com.data.repository.follow.FollowRepository
import example.com.service.FollowService
import example.com.util.ApiResponseMessages.USER_NOT_FOUND
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.followUser(
    followService: FollowService
) {
    post("/api/following/follow") {
        val request = call.receiveNullable<FollowUpdateRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        if (followService.followUserIfExist(request)) {
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
                    USER_NOT_FOUND
                )
            )
        }
    }
}

fun Route.unfollowUser(followService: FollowService) {
    delete("/api/following/unfollow") {
        val request = call.receiveNullable<FollowUpdateRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }
        if (followService.unFollowUserIfExist(request)) {
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
                    message = USER_NOT_FOUND
                )
            )
        }
    }
}