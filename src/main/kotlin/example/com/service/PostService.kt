package example.com.service

import example.com.data.models.Post
import example.com.data.repository.post.PostRepository
import example.com.data.request.CreatePostRequest
import example.com.util.Constants.DEFAULT_POST_PAGE_SIZE

class PostService(
   private val  repository: PostRepository
) {
    suspend fun createPostIfUserExist(request : CreatePostRequest) = repository.createPostIfUserExist(
        Post(
            imageUrl = "",
            userId = request.userId,
            timestamp = System.currentTimeMillis(),
            description = request.description
        )
    )

    suspend fun getPostForFollows(
        userId: String,
        page : Int = 0 ,
        pageSize : Int = DEFAULT_POST_PAGE_SIZE
    ) : List<Post> {
       return  repository.getPostByFollows(userId,page,pageSize)
    }

}