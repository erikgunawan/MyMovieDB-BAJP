package id.ergun.mymoviedb.ui.view.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.ergun.mymoviedb.BuildConfig
import id.ergun.mymoviedb.R
import id.ergun.mymoviedb.databinding.TvShowItemsBinding
import id.ergun.mymoviedb.domain.model.TvShow
import id.ergun.mymoviedb.util.loadImage

/**
 * Created by alfacart on 21/10/20.
 */
class TvShowAdapter : PagedListAdapter<TvShowVR, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    var favorite: Boolean = false

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowVR>() {
            override fun areItemsTheSame(oldItem: TvShowVR, newItem: TvShowVR): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowVR, newItem: TvShowVR): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder =
        TvShowViewHolder(
            TvShowItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class TvShowViewHolder(private val binding: TvShowItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowVR) {
            with(itemView) {
                binding.tvTitle.text = tvShow.title
                binding.viewRating.tvRating.text = tvShow.voteAverage.toString()
                binding.tvTagLine.text = tvShow.tagLine
                setOnClickListener {
                    val bundle = Bundle()
                    bundle.putParcelable(TvShow.EXTRA_TV_SHOW, TvShowVR.toModel(tvShow))

                    val actionId = if (favorite)
                        R.id.action_favoriteFragment_to_tvShowDetailFragment
                    else
                        R.id.action_tvShowFragment_to_tvShowDetailFragment

                    Navigation.findNavController(it).navigate(actionId, bundle)
                }
                binding.ivPoster.loadImage(BuildConfig.IMAGE_URL + tvShow.posterPath)
            }
        }
    }
}