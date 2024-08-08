package example.com.data.request

import example.com.data.util.ParentType

data class LikeUpdateRequest(
    val parentId : String,
    val parentType: Int
)
