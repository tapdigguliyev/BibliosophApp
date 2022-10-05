package com.example.bibliosoph.di

import android.content.Context
import androidx.room.Room
import com.example.bibliosoph.model.api.BooksApi
import com.example.bibliosoph.model.repository.BibliosophRepository
import com.example.bibliosoph.model.repository.BibliosophRepositoryImpl
import com.example.bibliosoph.model.repository.GoogleBooksRepository
import com.example.bibliosoph.model.room.BibliosophDatabase
import com.example.bibliosoph.model.room.migrations.migration_1_2
import com.example.bibliosoph.other.Constants.Companion.BASE_URL
import com.example.bibliosoph.other.Constants.Companion.BIBLIOSOPH_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBibliosophDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        BibliosophDatabase::class.java,
        BIBLIOSOPH_DATABASE_NAME)
        .addMigrations(migration_1_2)
        .build()

    @Provides
    @Singleton
    fun provideBookDao(db: BibliosophDatabase) = db.bookDao()

    @Provides
    @Singleton
    fun provideGenreDao(db: BibliosophDatabase) = db.genreDao()

    @Provides
    @Singleton
    fun provideBibliosophRepository(bibliosophRepositoryImpl: BibliosophRepositoryImpl): BibliosophRepository {
        return bibliosophRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideClient(logging: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): BooksApi = retrofit.create(BooksApi::class.java)

    @Provides
    @Singleton
    fun provideGoogleBooksRepository(booksApi: BooksApi) = GoogleBooksRepository(booksApi)
}