package id.ergun.mymoviedb.data.remote

import id.ergun.mymoviedb.util.Resource
import id.ergun.mymoviedb.util.testing.EspressoIdlingResource
import retrofit2.Response
import timber.log.Timber

/**
 * Created by alfacart on 20/11/20.
 */

fun <T, R> Response<T>.getResult(transform: (T) -> (R)): Resource<R> {
    try {
        val response = this
        if (response.isSuccessful && response.body() != null) {
            val body = transform(response.body()!!)
            if (body != null) {
                return success(body)
            }
        }

        return error("Something when wrong")
    } catch (e: Exception) {
        return error(e.message ?: e.toString())
    }
}

private fun <T> success(body: T): Resource<T> {
    EspressoIdlingResource.decrement()
    return Resource.success(body)
}

private fun <T> error(message: String): Resource<T> {
    EspressoIdlingResource.decrement()
    Timber.e(message)
    return Resource.error(message)
}