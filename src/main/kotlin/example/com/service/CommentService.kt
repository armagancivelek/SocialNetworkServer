package example.com.service

import example.com.data.models.Comment
import example.com.data.repository.comment.CommentRepository
import example.com.data.request.CreateCommentRequest
import example.com.util.Constants


class CommentService(
    private val repository: CommentRepository
) {
    suspend fun  createComment(createCommentRequest: CreateCommentRequest) : ValidationEvents {
        createCommentRequest.apply {
            if (comment.isBlank() || userId.isBlank() || postId.isBlank()) {
                return ValidationEvents.FieldEmpty
            }
            if (comment.length > Constants.MAX_COMMENT_LENGTH) {
                return ValidationEvents.ErrorCommentTooLong
            }
        }
        repository.createComment(
            Comment(
                comment = createCommentRequest.comment,
                userId = createCommentRequest.userId,
                postId = createCommentRequest.postId,
                timestamp = System.currentTimeMillis()
            )
        )
        return ValidationEvents.Success
    }

    suspend fun deleteComment(commentId : String) : Boolean{
       return  repository.deleteComment(commentId)
    }

    suspend fun getCommentsForPost(postId:String) :List<Comment> {
        return repository.getCommentForPost(postId)
    }
    sealed class  ValidationEvents {
        object  FieldEmpty : ValidationEvents()
        object Success : ValidationEvents()
        object ErrorCommentTooLong : ValidationEvents()
    }
}