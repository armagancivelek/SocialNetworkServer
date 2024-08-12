package example.com.data.repository.post

import example.com.data.models.Post
import example.com.data.responses.PostResponse
import example.com.util.Constants
import example.com.util.Constants.DEFAULT_POST_PAGE_SIZE

interface PostRepository {
    suspend fun createPost(post: Post): Boolean
    suspend fun  deletePost(postId: String)
    suspend fun  getPostByFollows(
        userId : String,
        page: Int,
        pageSize:Int = DEFAULT_POST_PAGE_SIZE
    ) : List<Post>

    suspend fun getPost(postId:String) : Post?

    suspend fun getPostsForProfile(
        ownUserId: String,
        userId: String,
        page: Int = 0,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): List<PostResponse>
}