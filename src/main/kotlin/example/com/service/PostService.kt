package example.com.service

import example.com.data.models.Post
import example.com.data.repository.post.PostRepository
import example.com.data.request.CreatePostRequest
import example.com.util.Constants.DEFAULT_POST_PAGE_SIZE

 class PostService(
   internal val  repository: PostRepository
) {
    suspend fun createPostIfUserExist(request : CreatePostRequest,userId: String) = repository.createPostIfUserExist(
        Post(
            imageUrl = "",
            userId = userId,
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

    suspend fun getPost(postId: String) : Post?  = repository.getPost(postId)
    suspend fun deletePost(postId: String)   = repository.deletePost(postId)

}