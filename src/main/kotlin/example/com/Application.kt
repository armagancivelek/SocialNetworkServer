package example.com

import example.com.di.mainModule
import example.com.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import org.koin.ktor.plugin.Koin


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}

fun Application.module() {
    install(Koin) {
        modules(mainModule)
    }
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
    configureRouting()
    configureValidation()
}
