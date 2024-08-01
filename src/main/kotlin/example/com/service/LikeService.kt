package example.com.service

import example.com.data.models.Like
import example.com.data.repository.likes.LikesRepository
import org.litote.kmongo.eq

class LikeService (
    private val repository: LikesRepository
){

    suspend fun likeParent(userId: String, parentId: String) : Boolean{
        return  repository.likeParent(userId,parentId)
    }

    suspend fun unlikeParent(userId: String, parentId: String) : Boolean{
        return  repository.unlikeParent(userId,parentId)
    }

     suspend fun deleteLikesForParent(parentId: String) {
        repository.deleteLikesForParent(parentId)
    }
}