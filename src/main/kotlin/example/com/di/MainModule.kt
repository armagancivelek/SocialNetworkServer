package example.com.di

import example.com.repository.user.UserRepository
import example.com.repository.user.UserRepositoryImp
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


const val DATABASE_NAME = "social_network_twitch"


val mainModule  = module {
    single {
        val client = KMongo.createClient().coroutine
        client.getDatabase(DATABASE_NAME)
    }

    single<UserRepository>{
        UserRepositoryImp(get())
    }
}