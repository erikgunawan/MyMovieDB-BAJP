package id.ergun.mymoviedb.ui.datasource.tvshow

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import id.ergun.mymoviedb.data.Const
import id.ergun.mymoviedb.domain.usecase.tvshow.TvShowUseCase
import id.ergun.mymoviedb.ui.view.tvshow.TvShowVR
import id.ergun.mymoviedb.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by alfacart on 01/12/20.
 */

class TvShowKeyedDataSource(
    private val useCase: TvShowUseCase
) : PageKeyedDataSource<Int, TvShowVR>() {

    var state: MutableLiveData<Resource<*>> = MutableLiveData()

    var favoritePage: Boolean = false

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvShowVR>
    ) {
        if (favoritePage) {
            scope.launch {
                try {
                    val response = useCase.getFavoriteTvShows()
                    if (response.status == Resource.Status.SUCCESS && !response.data.isNullOrEmpty()) {
                        state.postValue(response)
                        val items = TvShowVR.transform(response.data).toMutableList()
                        callback.onResult(items, null, 2)
                        return@launch
                    }

                    state.postValue(Resource.emptyData("Data tidak ditemukan", null))
                }
                catch (exception: Exception) {
                    Timber.e(exception)
                    state.postValue(Resource.error("Something when wrong", data = null))
                }
            }
            return
        }

        state.postValue(Resource.loading(null))
        scope.launch {
            try {
                val response = useCase.getTvShows(page = Const.INITIAL_PAGE)
                state.postValue(response)
                if (response.status == Resource.Status.SUCCESS) {
                    val items = TvShowVR.transform(response.data ?: arrayListOf()).toMutableList()
                    callback.onResult(items, null, 2)
                }
            }
            catch (exception: Exception) {
                Timber.e(exception)
                state.postValue(Resource.error("Something when wrong", data = null))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TvShowVR>) {
        // not implemented
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TvShowVR>) {
        if (favoritePage) {
            return
        }
        scope.launch {
            try {
                val response = useCase.getTvShows(page = params.key)
                if (response.status == Resource.Status.SUCCESS) {
                    state.postValue(response)
                    val items = TvShowVR.transform(response.data!!).toMutableList()
                    callback.onResult(items, params.key + 1)
                }
            }
            catch (exception: Exception) {
                Timber.e(exception)
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }

}