package id.ergun.mymoviedb.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ergun.mymoviedb.data.repository.movie.MovieRepository
import id.ergun.mymoviedb.data.repository.movie.MovieRepositoryImpl
import id.ergun.mymoviedb.data.repository.tvshow.TvShowRepository
import id.ergun.mymoviedb.data.repository.tvshow.TvShowRepositoryImpl

/**
 * Created by alfacart on 21/10/20.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository
    @Binds
    abstract fun bindTvShowRepository(impl: TvShowRepositoryImpl): TvShowRepository
}