package example.com.service

import example.com.data.models.Comment
import example.com.data.repository.comment.CommentRepository
import example.com.data.request.CreateCommentRequest
import example.com.util.Constants


class CommentService(
    private val repository: CommentRepository
) {
    suspend fun createComment(createCommentRequest: CreateCommentRequest, userId: String): ValidationEvents {
        createCommentRequest.apply {
            if (comment.isBlank() || postId.isBlank()) {
                return ValidationEvents.FieldEmpty
            }
            if (comment.length > Constants.MAX_COMMENT_LENGTH) {
                return ValidationEvents.ErrorCommentTooLong
            }
        }
        repository.createComment(
            Comment(
                comment = createCommentRequest.comment,
                userId = userId,
                postId = createCommentRequest.postId,
                timestamp = System.currentTimeMillis()
            )
        )
        return ValidationEvents.Success
    }

    suspend fun deleteComment(commentId: String): Boolean {
        return repository.deleteComment(commentId)
    }

    suspend fun getCommentsForPost(postId: String): List<Comment> {
        return repository.getCommentsForPost(postId)
    }

    suspend fun deleteCommentsForPostId(postId: String) {
        repository.deleteCommentsFromPost(postId)
    }

    suspend fun getCommentById(commentId: String) = repository.getComment(commentId)
    sealed class ValidationEvents {
        data object FieldEmpty : ValidationEvents()
        data object Success : ValidationEvents()
        data object ErrorCommentTooLong : ValidationEvents()
    }
}