package example.com.plugins

import example.com.repository.user.UserRepository
import example.com.repository.user.UserRepositoryImp
import example.com.routes.createUserRoute
import example.com.routes.loginUser
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userRepository : UserRepository by inject()
    routing {
      createUserRoute(userRepository)
        loginUser(userRepository)
    }
}
