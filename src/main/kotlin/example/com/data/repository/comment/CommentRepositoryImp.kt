package example.com.data.repository.comment

import example.com.data.models.Comment
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class CommentRepositoryImp (
    db:CoroutineDatabase

) : CommentRepository {

    val comments = db.getCollection<Comment>()
    override suspend fun createComment(comment: Comment) {
        comments.insertOne(comment)
    }

    override suspend fun deleteComment(commentId: String): Boolean {
         return comments.deleteOneById(commentId).deletedCount > 0
    }

    override suspend fun getCommentsForPost(postId: String): List<Comment> {
         return comments.find(Comment::postId eq postId).toList()
    }

    override suspend fun deleteCommentsFromPost(postId: String): Boolean {
         return comments.deleteMany(
            Comment::postId eq postId
        ).wasAcknowledged()
    }


    override suspend fun getComment(commentId: String): Comment? {
         return comments.findOneById(commentId)
    }
}