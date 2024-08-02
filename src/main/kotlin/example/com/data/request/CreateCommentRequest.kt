package example.com.data.request

data class CreateCommentRequest(
    val comment : String,
    val postId : String,
    val userId :String
)
