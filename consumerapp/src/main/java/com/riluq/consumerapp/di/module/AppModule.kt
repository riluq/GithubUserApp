package com.riluq.consumerapp.di.module

import android.app.Application
import android.content.Context
import com.riluq.consumerapp.data.source.GithubUserRepository
import com.riluq.consumerapp.data.source.local.LocalRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideGuthubUserRepository(
        localRepository: LocalRepository
    ) = GithubUserRepository(localRepository)

    @Provides
    @Singleton
    internal fun provideLocalRepository(context: Context) =
        LocalRepository(context)

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

}