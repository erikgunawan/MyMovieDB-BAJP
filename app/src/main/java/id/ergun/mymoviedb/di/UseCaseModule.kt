package id.ergun.mymoviedb.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ergun.mymoviedb.domain.usecase.movie.MovieUseCase
import id.ergun.mymoviedb.domain.usecase.movie.MovieUseCaseImpl
import id.ergun.mymoviedb.domain.usecase.tvshow.TvShowUseCase
import id.ergun.mymoviedb.domain.usecase.tvshow.TvShowUseCaseImpl

/**
 * Created by alfacart on 21/10/20.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindMovieUseCase(impl: MovieUseCaseImpl): MovieUseCase
    @Binds
    abstract fun bindTvShowUseCase(impl: TvShowUseCaseImpl): TvShowUseCase
}