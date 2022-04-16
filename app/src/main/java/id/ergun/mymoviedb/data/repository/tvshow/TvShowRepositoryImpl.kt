package id.ergun.mymoviedb.data.repository.tvshow

import com.google.gson.Gson
import id.ergun.mymoviedb.data.local.model.TvShowLocal
import id.ergun.mymoviedb.data.local.room.dao.TvShowDao
import id.ergun.mymoviedb.data.remote.ApiService
import id.ergun.mymoviedb.data.remote.getResult
import id.ergun.mymoviedb.data.remote.model.TvShowResponse
import id.ergun.mymoviedb.domain.model.TvShow
import id.ergun.mymoviedb.util.Resource
import id.ergun.mymoviedb.util.testing.EspressoIdlingResource
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by alfacart on 21/10/20.
 */
class TvShowRepositoryImpl @Inject constructor(private val remoteData: ApiService, private val localData: TvShowDao) : TvShowRepository {
    override suspend fun getTvShows(page: Int): Resource<ArrayList<TvShow>> {
        EspressoIdlingResource.increment()
        return try {
            remoteData.getTvShows(page = page).getResult {
                TvShowResponse.mapToDomainModelList(it)
            }
        }
        catch (exception: Exception) {
            Resource.error("Something when wrong")
        }
    }
    override suspend fun getTvShowDetail(id: Int): Resource<TvShow> {
        EspressoIdlingResource.increment()
        return remoteData.getTvShowDetail(id = id.toString()).getResult {
            TvShowResponse.mapToDomainModel(it)
        }
    }

    override suspend fun getFavoriteTvShows(): Resource<ArrayList<TvShow>> {
        return try {
            return Resource.success(
                TvShowLocal.mapToDomainModelList(localData.getTvShows()))
        }
        catch (exception: Exception) {
            Timber.e(exception.toString())
            Resource.error("Something when wrong")
        }
    }

    override suspend fun getFavoriteTvShow(id: Int): Resource<TvShow> {
        val tvShow = localData.getTvShow(id)
        Timber.e(Gson().toJson(tvShow))
        return if (tvShow != null) {
            Resource.success(TvShowLocal.mapToDomainModel(tvShow))
        } else {
            Resource.error("Data not found")
        }
    }

    override suspend fun addToFavorite(tvShow: TvShow): Long {
        return localData.insertTvShow(TvShowLocal.mapFromDomainModel(tvShow))
    }

    override suspend fun removeFromFavorite(id: Int): Int {
        return localData.deleteById(id)
    }
}