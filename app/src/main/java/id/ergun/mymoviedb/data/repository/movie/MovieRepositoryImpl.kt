package id.ergun.mymoviedb.data.repository.movie

import com.google.gson.Gson
import id.ergun.mymoviedb.data.local.model.MovieLocal
import id.ergun.mymoviedb.data.local.room.dao.MovieDao
import id.ergun.mymoviedb.data.remote.ApiService
import id.ergun.mymoviedb.data.remote.getResult
import id.ergun.mymoviedb.data.remote.model.MovieResponse
import id.ergun.mymoviedb.domain.model.Movie
import id.ergun.mymoviedb.util.Resource
import id.ergun.mymoviedb.util.testing.EspressoIdlingResource
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Created by alfacart on 21/10/20.
 */
class MovieRepositoryImpl @Inject constructor(private val remoteData: ApiService, private val localData: MovieDao) : MovieRepository {
    override suspend fun getMovies(): Resource<ArrayList<Movie>> {
        EspressoIdlingResource.increment()
        return try {
            remoteData.getMovies(page = 1).getResult {
                MovieResponse.mapToDomainModelList(it)
            }
        }
        catch (exception: Exception) {
            Resource.error("Terjadi kesalahan")
        }
    }

    override suspend fun getMovieDetail(id: Int): Resource<Movie> {
        EspressoIdlingResource.increment()
        return remoteData.getMovieDetail(id = id.toString()).getResult {
            MovieResponse.mapToDomainModel(it)
        }
    }

    override suspend fun getFavoriteMovies(): Resource<ArrayList<Movie>> {
        return try {
            return Resource.success(
                MovieLocal.mapToDomainModelList(localData.getMovies()))
        }
        catch (exception: Exception) {
            Timber.e(exception.toString())
            Resource.error("Terjadi kesalahan")
        }
    }

    override suspend fun getFavoriteMovie(id: Int): Resource<Movie> {
        val movie = localData.getMovie(id)
        Timber.e(Gson().toJson(movie))
        return if (movie != null) {
            Resource.success(MovieLocal.mapToDomainModel(movie))
        } else {
            Resource.error("Data tidak ditemukan")
        }
    }

    override suspend fun addToFavorite(movie: Movie): Long {
        return localData.insertMovie(MovieLocal.mapFromDomainModel(movie))
    }

    override suspend fun removeFromFavorite(id: Int) {
        localData.deleteById(id)
    }
}