package id.ergun.mymoviedb.ui.view.tvshow.detail

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.ergun.mymoviedb.BuildConfig
import id.ergun.mymoviedb.R
import id.ergun.mymoviedb.databinding.TvShowDetailFragmentBinding
import id.ergun.mymoviedb.domain.model.TvShow
import id.ergun.mymoviedb.ui.view.favorite.FavoriteModel
import id.ergun.mymoviedb.ui.viewmodel.tvshow.TvShowViewModel
import id.ergun.mymoviedb.util.Const
import id.ergun.mymoviedb.util.eventbus.FavoriteEvent
import id.ergun.mymoviedb.util.loadImage
import id.ergun.mymoviedb.util.share
import org.greenrobot.eventbus.EventBus

/**
 * Created by alfacart on 14/04/22.
 */
@AndroidEntryPoint
class TvShowDetailFragment : Fragment() {

    private lateinit var binding: TvShowDetailFragmentBinding

    private val viewModel by viewModels<TvShowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TvShowDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        loadIntents()
        initActions()

        updateData(viewModel.tvShow)

        getTvShowDetail()
        checkFavorite()
        loadIntents()


    }

    private fun updateData(tvShow: TvShow) {
        with(binding) {
            tvTitle.text = tvShow.title
            tvTagLine.text = tvShow.tagLine
            tvOverview.text = tvShow.overview
            viewRating.tvRating.text = tvShow.voteAverage.toString()
            ivPoster.loadImage(BuildConfig.IMAGE_URL + tvShow.posterPath)
        }
    }

    private fun getTvShowDetail() {
        viewModel.tvShow.id?.let {
            viewModel.getTvShowDetail(it).observe(requireActivity()) { data ->
                val tvShow = data.data ?: return@observe
                updateData(tvShow)
            }
        }
    }

    private fun checkFavorite() {
        viewModel.getFavoriteTvShowById().observe(requireActivity()) { data ->
            viewModel.favorite.value = data.data != null
        }

        viewModel.favorite.observe(requireActivity()) {
            if (it == null) return@observe
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    if (it) R.drawable.ic_favorite else R.drawable.ic_favorite_unchecked
                )
            )
        }

        viewModel.favoriteState.observe(requireActivity()) {
            val message = if (it == FavoriteModel.Type.ADD_TO_FAVORITE)
                getString(R.string.message_add_to_favorite)
            else getString(R.string.message_remove_from_favorite)

            val snackBar = Snackbar.make(binding.container, message, Snackbar.LENGTH_LONG)
            snackBar.show()
        }
    }

    private fun addToFavorite() {
        viewModel.addToFavorite(viewModel.tvShow).observe(requireActivity()) {
            EventBus.getDefault().post(FavoriteEvent(Const.TV_SHOW_TYPE, true))
            viewModel.favorite.value = true
            viewModel.favoriteState.value = FavoriteModel.Type.ADD_TO_FAVORITE
        }
    }

    private fun removeFromFavorite() {
        if (viewModel.tvShow.id == null) return
        viewModel.removeFromFavorite(viewModel.tvShow.id!!).observe(requireActivity()) {
            EventBus.getDefault().post(FavoriteEvent(Const.TV_SHOW_TYPE, true))
            viewModel.favorite.value = false
            viewModel.favoriteState.value = FavoriteModel.Type.REMOVE_FROM_FAVORITE
        }
    }

    private fun loadIntents() {
        if (arguments?.get(TvShow.EXTRA_TV_SHOW) != null) {
            viewModel.setSelectedTvShow(arguments?.getParcelable(TvShow.EXTRA_TV_SHOW)!!)
        }
    }

    private fun initActions() {
        binding.fabFavorite.setOnClickListener {
            if (viewModel.favorite.value == true) removeFromFavorite()
            else addToFavorite()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                share(requireActivity(), "tv", viewModel.tvShow.id.toString())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val actionReminder = menu.findItem(R.id.reminderFragment)
        actionReminder?.isVisible = false
    }
}