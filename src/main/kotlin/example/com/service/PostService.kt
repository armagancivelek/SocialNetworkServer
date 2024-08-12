package example.com.service

import example.com.data.models.Post
import example.com.data.repository.post.PostRepository
import example.com.data.request.CreatePostRequest
import example.com.data.responses.PostResponse
import example.com.util.Constants
import example.com.util.Constants.DEFAULT_POST_PAGE_SIZE

 class PostService(
   internal val  repository: PostRepository
) {
     suspend fun createPost(request: CreatePostRequest, userId: String, imageUrl: String): Boolean {
         return repository.createPost(
             Post(
                 imageUrl = imageUrl,
                 userId = userId,
                 timestamp = System.currentTimeMillis(),
                 description = request.description
             )
         )
     }

    suspend fun getPostForFollows(
        userId: String,
        page : Int = 0 ,
        pageSize : Int = DEFAULT_POST_PAGE_SIZE
    ) : List<Post> {
       return  repository.getPostByFollows(userId,page,pageSize)
    }

    suspend fun getPost(postId: String) : Post?  = repository.getPost(postId)
    suspend fun deletePost(postId: String)   = repository.deletePost(postId)
     suspend fun getPostsForProfile(
         ownUserId: String,
         userId: String,
         page: Int = 0,
         pageSize: Int = Constants.DEFAULT_PAGE_SIZE
     ): List<PostResponse> {
         return repository.getPostsForProfile(ownUserId, userId, page, pageSize)
     }
}