package com.example.mapptuu.di


import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
    @Binds
    @Singleton
    @RemoteDataSource
    abstract fun bindsRemotePokemonDataSource(ds: ActivityRemote): CharacterDataSource

    @Binds
    @Singleton
    @LocalDataSource
    abstract fun bindsLocalPokemonDataSource(ds: CharacterLocalDataSource): CharacterDataSource

    @Binds
    @Singleton
    abstract  fun bindPokemonRepository(repository: CharacterRepositoryImpl): CharacterRepository
    @Binds
    @Singleton
    @RemoteDataSource
    abstract fun bindsRemotePlanetDataSource(ds: PlanetRemoteDataSource): PlanetDataSource

    @Binds
    @Singleton
    @LocalDataSource
    abstract fun bindsLocalPlanetDataSource(ds: PlanetLocalDataSource): PlanetDataSource

    @Binds
    @Singleton
    abstract fun bindPlanetRepository(repository: PlanetRepositoryImpl): PlanetRepository
    //abstract fun bindPokemonRepository(repository: PokemonFakeRemoteRepository): PokemonRepository
    //abstract fun bindPokemonRepository(repository: PokemonInMemoryRepository): PokemonRepository
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource