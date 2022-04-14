package id.ergun.mymoviedb.ui.view.movie.detail

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.ergun.mymoviedb.BuildConfig
import id.ergun.mymoviedb.R
import id.ergun.mymoviedb.databinding.MovieDetailFragmentBinding
import id.ergun.mymoviedb.domain.model.Movie
import id.ergun.mymoviedb.ui.view.favorite.FavoriteModel
import id.ergun.mymoviedb.ui.viewmodel.movie.MovieViewModel
import id.ergun.mymoviedb.util.Const
import id.ergun.mymoviedb.util.eventbus.FavoriteEvent
import id.ergun.mymoviedb.util.loadImage
import id.ergun.mymoviedb.util.share
import org.greenrobot.eventbus.EventBus

/**
 * Created by alfacart on 14/04/22.
 */
@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: MovieDetailFragmentBinding

    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        loadIntents()
        initActions()

        updateData(viewModel.movie)

        getMovieDetail()
        checkFavorite()
    }

    private fun updateData(movie: Movie) {
        with(binding) {
            tvTitle.text = movie.title
            tvTagLine.text = movie.tagLine
            tvOverview.text = movie.overview
            viewRating.tvRating.text = movie.voteAverage.toString()
            ivPoster.loadImage(BuildConfig.IMAGE_URL + movie.posterPath)
        }
    }

    private fun getMovieDetail() {
        viewModel.movie.id?.let {
            viewModel.getMovieDetail(it).observe(requireActivity()) { data ->
                val movie = data.data ?: return@observe
                updateData(movie)
            }
        }
    }

    private fun checkFavorite() {
        viewModel.getFavoriteMovieById().observe(requireActivity()) { data ->
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
        viewModel.addToFavorite(viewModel.movie).observe(requireActivity()) {
            EventBus.getDefault().post(FavoriteEvent(Const.MOVIE_TYPE, true))
            viewModel.favorite.value = true
            viewModel.favoriteState.value = FavoriteModel.Type.ADD_TO_FAVORITE
        }
    }

    private fun removeFromFavorite() {
        if (viewModel.movie.id == null) return
        viewModel.removeFromFavorite(viewModel.movie.id!!).observe(requireActivity()) {
            EventBus.getDefault().post(FavoriteEvent(Const.MOVIE_TYPE, true))
            viewModel.favorite.value = false
            viewModel.favoriteState.value = FavoriteModel.Type.REMOVE_FROM_FAVORITE
        }
    }

    private fun loadIntents() {
        if (arguments?.get(Movie.EXTRA_MOVIE) != null) {
            viewModel.setSelectedMovie(arguments?.getParcelable(Movie.EXTRA_MOVIE)!!)
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
                share(requireActivity(), "movie", viewModel.movie.id.toString())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val actionReminder = menu.findItem(R.id.reminderFragment)
        actionReminder?.isVisible = false
    }
}