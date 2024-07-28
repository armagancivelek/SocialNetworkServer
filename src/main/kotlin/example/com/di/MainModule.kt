package example.com.di

import example.com.repository.follow.FollowRepository
import example.com.repository.follow.FollowRepositoryImpl
import example.com.repository.post.PostRepository
import example.com.repository.post.PostRepositoryImp
import example.com.repository.user.UserRepository
import example.com.repository.user.UserRepositoryImp
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


const val DATABASE_NAME = "social_network_twitch"


val mainModule  = module {
    single<CoroutineDatabase> {
        val client = KMongo.createClient().coroutine
        client.getDatabase(DATABASE_NAME)
    }

    single<UserRepository>{
        UserRepositoryImp(get())
    }

    single<FollowRepository>{
        FollowRepositoryImpl(get())
    }

    single<PostRepository>{
        PostRepositoryImp(get())
    }
}