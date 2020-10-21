package id.ergun.mymoviedb.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

/**
 * Created by alfacart on 21/10/20.
 */
@Parcelize
class Movie(
    var id: Int? = null,
    var title: String = "",
    @DrawableRes var image: Int? = null,
    var overview: String = ""
): Parcelable