package id.ergun.mymoviedb.domain.model

import android.os.Parcelable
import id.ergun.mymoviedb.data.local.MovieDB
import kotlinx.parcelize.Parcelize
/**
 * Created by alfacart on 21/10/20.
 */
@Parcelize
class Movie(
    var id: Int? = null,
    var title: String = "",
    var overview: String = "",
    var voteAverage: Double = 0.0,
    var tagLine: String = "",
    var posterPath: String = ""
): Parcelable {

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"

        fun transform(movieDB: MovieDB): ArrayList<Movie> {
            val list = arrayListOf<Movie>()
            movieDB.getMovies().forEach {
                list.add(
                    Movie(
                        it.id,
                        it.title,
                        it.overview,
                        it.voteAverage,
                        it.tagLine,
                        it.posterPath
                    )
                )
            }
            return list
        }
    }
}