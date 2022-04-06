package id.ergun.mymoviedb.ui.viewmodel.tvshow

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ergun.mymoviedb.domain.model.TvShow
import id.ergun.mymoviedb.domain.usecase.tvshow.TvShowUseCase
import id.ergun.mymoviedb.ui.datasource.tvshow.TvShowDataSourceFactory
import id.ergun.mymoviedb.ui.datasource.tvshow.TvShowKeyedDataSource
import id.ergun.mymoviedb.ui.view.favorite.FavoriteModel
import id.ergun.mymoviedb.util.Resource
import javax.inject.Inject

/**
 * Created by alfacart on 21/10/20.
 */
@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val useCase: TvShowUseCase,
    private val dataSourceFactory: TvShowDataSourceFactory
) : ViewModel() {

    var favorite: MutableLiveData<Boolean> = MutableLiveData()

    var favoritePage: Boolean = false

    var favoriteState: MutableLiveData<FavoriteModel.Type> = MutableLiveData()

    lateinit var tvShow: TvShow

    fun setSelectedTvShow(tvShow: TvShow) {
        this.tvShow = tvShow
    }

    fun setFavorite(favoritePage: Boolean) {
        this.favoritePage = favoritePage
        dataSourceFactory.favoritePage = favoritePage
    }

    fun getTvShows() = dataSourceFactory.getTvShows()

    val tvShowState: LiveData<Resource<*>> =
        Transformations.switchMap(
            dataSourceFactory.liveData,
            TvShowKeyedDataSource::state
        )

    fun refresh() {
        dataSourceFactory.liveData.value?.invalidate()
    }

    fun getTvShowDetail(id: Int): LiveData<Resource<TvShow>> {
        return liveData { emit(useCase.getTvShowDetail(id)) }
    }

    private fun getFavoriteTvShow(id: Int) = liveData { emit(useCase.getFavoriteTvShow(id)) }

    fun getFavoriteTvShowById() = if (tvShow.id != null) getFavoriteTvShow(tvShow.id!!)
    else liveData {  }

    fun addToFavorite(tvShow: TvShow) = liveData {  emit(useCase.addToFavorite(tvShow)) }

    fun removeFromFavorite(id: Int) = liveData { emit(useCase.removeFromFavorite(id)) }
}