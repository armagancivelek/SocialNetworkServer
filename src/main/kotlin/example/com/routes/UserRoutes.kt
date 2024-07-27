package example.com.routes

import example.com.data.models.User
import example.com.data.request.CreateAccountRequest
import example.com.data.responses.BasicApiResponse
import example.com.di.DATABASE_NAME
import example.com.repository.user.UserRepository
import example.com.repository.user.UserRepositoryImp
import example.com.util.ApiResponseMessages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


fun Route.userRoutes() {
    val userRepository: UserRepository by inject()


    route("/") {

        get {
            call.respond(
                BasicApiResponse(
                    successful = true,
                    message = "merhaba"
                )
            )
        }

    }
    route("/api/user/create") {
        post {
            val request = call.receiveNullable<CreateAccountRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            try {
                val userExist = userRepository.getUserByEmail(request.email) != null

                if (userExist) {
                    call.respond(
                        BasicApiResponse(
                            false,
                            message = ApiResponseMessages.USER_ALREADY_EXISTS
                        )
                    )
                    return@post
                }

                if (request.email.isBlank() || request.password.isBlank() || request.username.isBlank()) {
                    call.respond(
                        BasicApiResponse(
                            false,
                            message = ApiResponseMessages.FIELDS_BLANK
                        )
                    )
                    return@post
                }

                userRepository.createUser(
                    User(
                        email = request.email,
                        username = request.username,
                        password = request.password,
                        profileImageUrl = "",
                        bio = "",
                        gitHubUrl = null,
                        instagramUrl = null,
                        linkedInUrl = null,
                        bannerUrl = null
                    )
                )
                call.respond(
                    BasicApiResponse(
                        successful = true
                    )
                )
            } catch (e: Exception) {
                println("Armagan Exception : ${e.message}")
                return@post
            }
        }
    }
}