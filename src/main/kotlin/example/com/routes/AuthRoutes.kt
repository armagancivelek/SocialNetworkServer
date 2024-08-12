package example.com.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import example.com.data.request.LoginRequest
import example.com.data.responses.AuthResponse
import example.com.data.responses.BasicApiResponse
import example.com.service.UserService
import example.com.util.ApiResponseMessages.INVALID_CREDENTIALS
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.loginUser(
    userService: UserService,
    jwtIssuer: String,
    jwtAudience: String,
    jwtSecret: String
) {


    post("/api/user/login") {
        val request = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        if (request.email.isBlank() || request.password.isBlank()) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val user = userService.getUserByEmail(request.email) ?: kotlin.run {
            call.respond(
                HttpStatusCode.OK,
                BasicApiResponse<Unit>(
                    successful = false,
                    message = INVALID_CREDENTIALS
                )
            )
            return@post
        }
        val isCorrectedPassword = userService.isValidPassword(
            enteredPassword = request.password,
            actualPassword = user.password
        )
        if (isCorrectedPassword) {
            val expiresIn = 1000L * 60L * 60L * 24L * 365L
            val token = JWT.create()
                .withClaim("userId", user.id)
                .withIssuer(jwtIssuer)
                .withAudience(jwtAudience)
                .withExpiresAt(Date(System.currentTimeMillis() + expiresIn))
                .sign(Algorithm.HMAC256(jwtSecret))
            call.respond(
                HttpStatusCode.OK,
                AuthResponse(
                    token = token
                )
            )
        } else {
            call.respond(
                HttpStatusCode.OK,
                BasicApiResponse<Unit>(
                    successful = false,
                    message = INVALID_CREDENTIALS
                )
            )
        }
    }
}


