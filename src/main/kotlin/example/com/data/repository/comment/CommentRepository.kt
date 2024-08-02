package example.com.data.repository.comment

import example.com.data.models.Comment

interface CommentRepository {

    suspend fun createComment(comment: Comment)
    suspend fun deleteComment(commentId: String) : Boolean
    suspend fun getCommentForPost(postId: String) : List<Comment>
    suspend fun getComment(commentId: String) : Comment?


}