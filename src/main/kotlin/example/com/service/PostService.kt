package example.com.service

import example.com.data.models.Post
import example.com.data.request.CreatePostRequest
import example.com.data.repository.post.PostRepository

class PostService(
   private val  postRepository: PostRepository
) {
    suspend fun createPostIfUserExist(request : CreatePostRequest) = postRepository.createPostIfUserExist(
        Post(
            imageUrl = "",
            userId = request.userId,
            timestamp = System.currentTimeMillis(),
            description = request.description
        )
    )

}