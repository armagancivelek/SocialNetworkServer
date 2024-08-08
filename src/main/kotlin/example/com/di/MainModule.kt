package example.com.di

import example.com.data.repository.activity.ActivityRepository
import example.com.data.repository.activity.ActivityRepositoryImpl
import example.com.data.repository.comment.CommentRepository
import example.com.data.repository.comment.CommentRepositoryImp
import example.com.data.repository.follow.FollowRepository
import example.com.data.repository.follow.FollowRepositoryImpl
import example.com.data.repository.likes.LikesRepository
import example.com.data.repository.likes.LikesRepositoryImp
import example.com.data.repository.post.PostRepository
import example.com.data.repository.post.PostRepositoryImp
import example.com.data.repository.user.UserRepository
import example.com.data.repository.user.UserRepositoryImp
import example.com.service.*
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


const val DATABASE_NAME = "social_network_twitch"


val mainModule = module {
    single<CoroutineDatabase> {
        val client = KMongo.createClient().coroutine
        client.getDatabase(DATABASE_NAME)
    }

    single<UserRepository> {
        UserRepositoryImp(get())
    }

    single<FollowRepository> {
        FollowRepositoryImpl(get())
    }

    single<PostRepository> {
        PostRepositoryImp(get())
    }

    single<LikesRepository> {
        LikesRepositoryImp(get())
    }

    single<CommentRepository> {
        CommentRepositoryImp(get())
    }

    single<ActivityRepository> {
        ActivityRepositoryImpl(get())
    }


    single {
        UserService(get())
    }

    single {
        PostService(get())
    }
    single {
        FollowService(get())
    }

    single<LikeService> {
        LikeService(get())
    }
    single<CommentService> {
        CommentService(get())
    }
    single<ActivityService> {
        ActivityService(get(),get(),get())
    }

}