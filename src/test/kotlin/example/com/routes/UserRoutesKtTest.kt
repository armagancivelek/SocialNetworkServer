package example.com.routes

import com.google.common.truth.Truth.assertThat
import example.com.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.koin.test.KoinTest
import kotlin.test.Test


internal class UserRoutesKtTest  :KoinTest {


    @Test
    fun `Create user, no body attached, responds with BadRequest`() {

        testApplication {
            application {
                configureRouting()
            }
            val client = createClient {
                }
            val request = client.post("/api/user/create") {
                method = HttpMethod.Post
                contentType()
            }
            assertThat(request.status).isEqualTo(HttpStatusCode.BadRequest)

        }
    }

//    @Test
//    fun `Create user, user already exists, responds with unsuccessful`() = runBlocking {
//        val user = User(
//            email = "test@test.com",
//            username = "test",
//            password = "test",
//            profileImageUrl = "",
//            bio = "",
//            gitHubUrl = null,
//            instagramUrl = null,
//            linkedInUrl = null,
//            bannerUrl = null
//        )
//        userRepository.createUser(user)
//        withTestApplication(
//            moduleFunction = {
//                configureSerialization()
//                install(Routing) {
//                    createUserRoute(userRepository)
//                }
//            }
//        ) {
//            val request = handleRequest(
//                method = HttpMethod.Post,
//                uri = "/api/user/create"
//            ) {
//                addHeader("Content-Type", "application/json")
//                val request = CreateAccountRequest(
//                    email = "test@test.com",
//                    username = "asdf",
//                    password = "asdf"
//                )
//                setBody(gson.toJson(request))
//            }
//
//            val response = gson.fromJson(
//                request.response.content ?: "",
//                BasicApiResponse::class.java
//            )
//            assertThat(response.successful).isFalse()
//            assertThat(response.message).isEqualTo(ApiResponseMessages.USER_ALREADY_EXISTS)
//        }
//    }

}