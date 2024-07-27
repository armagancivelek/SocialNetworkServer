package example.com

import example.com.di.mainModule
import example.com.plugins.*
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}

fun Application.module() {
  install(Koin) {
      modules(mainModule)

  }

    //val helloworld : String by inject<String>()
    println("helloworld")
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
    configureRouting()
}
