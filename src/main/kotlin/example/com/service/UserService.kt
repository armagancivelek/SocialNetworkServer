package example.com.service

import example.com.data.models.User
import example.com.data.request.CreateAccountRequest
import example.com.data.request.LoginRequest
import example.com.data.repository.user.UserRepository

class UserService(
    private val repository : UserRepository
) {

    suspend fun doesUserWithEmailExist( email : String) : Boolean {
        return repository.getUserByEmail(email) != null
    }
    suspend fun doesPasswordMatchForUser(request: LoginRequest) =
         repository.doesPasswordForUserMatch(
            enteredPassword = request.password,
            email = request.email
        )

    suspend fun  doesEmailBelongToUserId(email: String, userId: String)  = repository.doesEmailBelongToUserId(email,userId)

    suspend fun createUser(request: CreateAccountRequest) {
        repository.createUser(
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
    }

    suspend fun validateCreateAccountRequest(request : CreateAccountRequest) : ValidationEvent {
      if ( request.email.isBlank() || request.password.isBlank() || request.username.isBlank()) {
          return ValidationEvent.ErrorFieldEmpty
      }
        return  ValidationEvent.Success
    }

    sealed class ValidationEvent {
        data object ErrorFieldEmpty : ValidationEvent()
        data object Success : ValidationEvent()
    }
}

