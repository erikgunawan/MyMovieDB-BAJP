package id.ergun.mymoviedb.domain.model

import android.os.Parcelable
import id.ergun.mymoviedb.data.local.TvShowDB
import kotlinx.parcelize.Parcelize

/**
 * Created by alfacart on 21/10/20.
 */
@Parcelize
class TvShow(
    var id: Int? = null,
    var title: String = "",
    var overview: String = "",
    var voteAverage: Double = 0.0,
    var tagLine: String = "",
    var posterPath: String = ""
): Parcelable {

    companion object {
        const val EXTRA_TV_SHOW = "EXTRA_TV_SHOW"

        fun transform(tvShowDB: TvShowDB): ArrayList<TvShow> {
            val list = arrayListOf<TvShow>()
            tvShowDB.getTvShows().forEach {
                list.add(
                    TvShow(
                        it.id,
                        it.name,
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