package example.com.data.repository.likes

import example.com.data.models.Like
import example.com.data.models.User
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class LikesRepositoryImp(private val db: CoroutineDatabase) : LikesRepository {

    val likes = db.getCollection<Like>()
    val users = db.getCollection<User>()
    override suspend fun likeParent(userId: String, parentId: String): Boolean {
        if (users.findOneById(userId) == null) {
            return false
        } else {
            likes.insertOne(Like(userId, parentId))
            return true
        }
    }

    override suspend fun unlikeParent(userId: String, parentId: String): Boolean {
        if (users.findOneById(userId) == null) {
            return false
        } else {
            likes.deleteOne(
                   and(
                       Like::userId eq userId,
                       Like::parentId eq parentId
                   )
            )
            return true
        }
    }

    override suspend fun deleteLikesForParent(parentId: String) {
        likes.deleteMany(Like::parentId eq parentId)
    }

}