package com.example.mapptuu.di

import com.example.mapptuu.data.remote.activity.ActivityApi
import com.example.mapptuu.data.remote.activityType.ActivityTypeApi
import com.example.mapptuu.data.remote.plan.PlanApi
import com.example.mapptuu.data.remote.user.UserApi
import com.example.mapptuu.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authRepository: Lazy<AuthRepository>
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()

                val token = runBlocking { authRepository.get().getCurrentUserToken() }

                if (!token.isNullOrBlank()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://vercel-node-mapp-tuu.vercel.app/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @Provides
    @Singleton
    fun provideActivityApi(retrofit: Retrofit): ActivityApi {
        return retrofit.create(ActivityApi::class.java)
    }

    @Provides
    @Singleton
    fun provideActivityTypeApi(retrofit: Retrofit): ActivityTypeApi {
        return retrofit.create(ActivityTypeApi::class.java)
    }

    @Provides
    @Singleton
    fun providePlansApi(retrofit: Retrofit): PlanApi {
        return retrofit.create(PlanApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
}
