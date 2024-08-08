package example.com.routes

import example.com.data.request.CreateCommentRequest
import example.com.data.request.DeleteCommentRequest
import example.com.data.request.FollowUpdateRequest
import example.com.data.responses.BasicApiResponse
import example.com.service.ActivityService
import example.com.service.CommentService
import example.com.service.LikeService
import example.com.service.UserService
import example.com.util.ApiResponseMessages
import example.com.util.QueryParams
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createComment(
    commentService: CommentService,
    activityService: ActivityService
) {
    authenticate {
        post("/api/comment/create") {
            val request = call.receiveNullable<CreateCommentRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val userId = call.userId

            when (commentService.createComment(request,userId)){
                CommentService.ValidationEvents.ErrorCommentTooLong -> {
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(
                            successful = false,
                            message = ApiResponseMessages.COMMENT_TOO_LONG

                        )
                    )
                }
                CommentService.ValidationEvents.FieldEmpty -> {
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(
                            successful = false,
                            message = ApiResponseMessages.FIELDS_BLANK

                        )
                    )
                }
                CommentService.ValidationEvents.Success -> {
                    activityService.addCommentActivity(
                        byUserId = userId,
                        postId = request.postId,

                    )
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(
                            successful = true,
                        )
                    )
                }
            }
        }
    }

}

fun Route.getCommentsForPost(
    commentService: CommentService,
) {
      authenticate {
          get("/api/comment/get"){
              val postId = call.parameters[QueryParams.PARAM_POST_ID] ?: kotlin.run {
                  call.respond(HttpStatusCode.BadRequest)
                  return@get
              }
              val comments = commentService.getCommentsForPost(postId)
              call.respond(
                  HttpStatusCode.OK,
                  comments
              )
          }
      }
}

fun Route.deleteComment(
    commentService: CommentService,
    likeService:LikeService
) {
    authenticate {
        delete("/api/comment/delete"){
            val request = call.receiveNullable<DeleteCommentRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val comment = commentService.getCommentById(request.commentId)
            if(comment?.userId != call.userId) {
                call.respond(HttpStatusCode.Unauthorized)
                return@delete
            }
            val deleted = commentService.deleteComment(request.commentId)
            if (deleted) {
                likeService.deleteLikesForParent(request.commentId)
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(
                        successful = true
                    )
                )
            }else {
                call.respond(
                    HttpStatusCode.NotFound,
                    BasicApiResponse(
                        successful = false
                    )
                )
            }
        }
    }
}