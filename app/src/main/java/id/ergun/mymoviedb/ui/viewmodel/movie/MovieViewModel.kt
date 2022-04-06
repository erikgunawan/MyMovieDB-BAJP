package id.ergun.mymoviedb.ui.viewmodel.movie

import androidx.lifecycle.*
import androidx.paging.PagedList
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ergun.mymoviedb.domain.model.Movie
import id.ergun.mymoviedb.domain.usecase.movie.MovieUseCase
import id.ergun.mymoviedb.ui.datasource.movie.MovieDataSourceFactory
import id.ergun.mymoviedb.ui.datasource.movie.MovieKeyedDataSource
import id.ergun.mymoviedb.ui.view.favorite.FavoriteModel
import id.ergun.mymoviedb.ui.view.movie.MovieVR
import id.ergun.mymoviedb.util.Resource
import javax.inject.Inject

/**
 * Created by alfacart on 21/10/20.
 */
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val useCase: MovieUseCase,
    private val dataSourceFactory: MovieDataSourceFactory
) : ViewModel() {

    var favorite: MutableLiveData<Boolean> = MutableLiveData()

    var favoritePage: Boolean = false

    var favoriteState: MutableLiveData<FavoriteModel.Type> = MutableLiveData()

    lateinit var movie: Movie

    fun setFavorite(favoritePage: Boolean) {
        this.favoritePage = favoritePage
        dataSourceFactory.favoritePage = favoritePage
    }

    fun setSelectedMovie(movie: Movie) {
        this.movie = movie
    }

    fun getMovies(): LiveData<PagedList<MovieVR>> = dataSourceFactory.getMovies()

    val movieState: LiveData<Resource<*>> =
        Transformations.switchMap(
            dataSourceFactory.liveData,
            MovieKeyedDataSource::state
        )

    fun refresh() {
        dataSourceFactory.liveData.value?.invalidate()
    }

    fun getMovieDetail(id: Int): LiveData<Resource<Movie>> {
        return liveData { emit(useCase.getMovieDetail(id)) }
    }

    private fun getFavoriteMovie(id: Int) = liveData { emit(useCase.getFavoriteMovie(id)) }

    fun getFavoriteMovieById() = if (movie.id != null) getFavoriteMovie(movie.id!!)
    else liveData {  }

    fun addToFavorite(movie: Movie) = liveData {  emit(useCase.addToFavorite(movie)) }

    fun removeFromFavorite(id: Int) = liveData { emit(useCase.removeFromFavorite(id)) }
}