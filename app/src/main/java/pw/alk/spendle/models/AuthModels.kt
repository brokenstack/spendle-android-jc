package pw.alk.spendle.models

data class RegisterRequest(
    val email: String,
    val password: String
)

data class RegisterResponse(
    val message: String,
    val token: String
)
