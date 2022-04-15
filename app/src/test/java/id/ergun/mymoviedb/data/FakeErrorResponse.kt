package id.ergun.mymoviedb.data

import id.ergun.mymoviedb.util.Resource

/**
 * Created by alfacart on 24/11/20.
 */
object FakeErrorResponse {

    fun <T> getError(): Resource<T> {
        return Resource(
            data = null,
            status = Resource.Status.ERROR,
            message = "Something when wrong"
        )
    }
}