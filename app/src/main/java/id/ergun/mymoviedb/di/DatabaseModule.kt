package id.ergun.mymoviedb.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.ergun.mymoviedb.data.local.room.AppDatabase
import javax.inject.Singleton

/**
 * Created by alfacart on 30/11/20.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun getAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "MyMovieDB.db"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun getMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()

    @Provides
    @Singleton
    fun getTvShowDao(appDatabase: AppDatabase) = appDatabase.tvShowDao()
}