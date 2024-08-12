package example.com.service

import example.com.data.models.Comment
import example.com.data.repository.comment.CommentRepository
import example.com.data.repository.user.UserRepository
import example.com.data.request.CreateCommentRequest
import example.com.util.Constants


class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
) {
    suspend fun createComment(createCommentRequest: CreateCommentRequest, userId: String): ValidationEvent {
        createCommentRequest.apply {
            if(comment.isBlank() || postId.isBlank()) {
                return ValidationEvent.ErrorFieldEmpty
            }
            if(comment.length > Constants.MAX_COMMENT_LENGTH) {
                return ValidationEvent.ErrorCommentTooLong
            }
        }
        val user = userRepository.getUserById(userId) ?: return ValidationEvent.UserNotFound
        commentRepository.createComment(
            Comment(
                username = user.username,
                profileImageUrl = user.profileImageUrl,
                likeCount = 0,
                comment = createCommentRequest.comment,
                userId = userId,
                postId = createCommentRequest.postId,
                timestamp = System.currentTimeMillis()
            )
        )
        return ValidationEvent.Success
    }

    suspend fun deleteComment(commentId: String): Boolean {
        return commentRepository.deleteComment(commentId)
    }

    suspend fun getCommentsForPost(postId: String): List<Comment> {
        return commentRepository.getCommentsForPost(postId)
    }

    suspend fun deleteCommentsForPostId(postId: String) {
        commentRepository.deleteCommentsFromPost(postId)
    }

    suspend fun getCommentById(commentId: String) = commentRepository.getComment(commentId)
    sealed class ValidationEvent {
        data object ErrorFieldEmpty : ValidationEvent()
        data object ErrorCommentTooLong : ValidationEvent()
        data object UserNotFound: ValidationEvent()
        data object Success : ValidationEvent()
    }
}