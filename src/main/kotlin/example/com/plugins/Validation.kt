package example.com.plugins

import example.com.data.request.LoginRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*


fun Application.configureValidation() {
    install(RequestValidation) {
        validate<LoginRequest> { loginData ->
            if (loginData.email.isBlank() || loginData.password.isBlank()) {
                  ValidationResult.Invalid("Hatali göndeirim")
            }
            else ValidationResult.Valid
        }
    }
    install(StatusPages){
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.reasons.joinToString())
        }
    }
}