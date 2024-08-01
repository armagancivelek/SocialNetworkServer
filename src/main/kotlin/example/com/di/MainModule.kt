package example.com.di

import example.com.data.repository.follow.FollowRepository
import example.com.data.repository.follow.FollowRepositoryImpl
import example.com.data.repository.likes.LikesRepository
import example.com.data.repository.likes.LikesRepositoryImp
import example.com.data.repository.post.PostRepository
import example.com.data.repository.post.PostRepositoryImp
import example.com.data.repository.user.UserRepository
import example.com.data.repository.user.UserRepositoryImp
import example.com.service.FollowService
import example.com.service.LikeService
import example.com.service.PostService
import example.com.service.UserService
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
}