package example.com.routes

import example.com.data.request.CreateAccountRequest
import example.com.data.responses.BasicApiResponse
import example.com.service.UserService
import example.com.util.ApiResponseMessages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createUserRoute(
    userService: UserService
) {

    post("/api/user/create") {
        val request = call.receiveNullable<CreateAccountRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        if (userService.doesUserWithEmailExist(request.email)) {
            println("User exist")
            call.respond(
                BasicApiResponse(
                    false,
                    message = ApiResponseMessages.USER_ALREADY_EXISTS
                )
            )
            return@post
        }
        when (userService.validateCreateAccountRequest(request)) {
            is UserService.ValidationEvent.ErrorFieldEmpty -> {
                call.respond(
                    BasicApiResponse(
                        false,
                        message = ApiResponseMessages.FIELDS_BLANK
                    )
                )
            }
            UserService.ValidationEvent.Success -> {
                userService.createUser(request)
                call.respond(
                    BasicApiResponse(
                        successful = true
                    )
                )
            }
        }
    }
}