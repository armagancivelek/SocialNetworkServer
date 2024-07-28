package example.com.repository.post

import example.com.data.models.Post
import example.com.util.DEFAULT_POST_PAGE_SIZE

interface PostRepository {
    suspend fun  createPostIfUserExist(post : Post) : Boolean
    suspend fun  deletePost(postId: String)
    suspend fun  getPostByFollows(
        userId : String,
        page: Int,
        pageSize:Int = DEFAULT_POST_PAGE_SIZE
    ) : List<Post>


}