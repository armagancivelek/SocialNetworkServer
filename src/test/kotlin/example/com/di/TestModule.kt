package example.com.di

import example.com.repository.user.FakeUserRepository
import org.koin.dsl.module


internal val testModule = module {
    single{
        FakeUserRepository()
    }
}