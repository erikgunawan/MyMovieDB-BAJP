package id.ergun.mymoviedb.ui.view.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.ergun.mymoviedb.databinding.MovieFragmentBinding
import id.ergun.mymoviedb.ui.viewmodel.movie.MovieViewModel
import id.ergun.mymoviedb.util.Const
import id.ergun.mymoviedb.util.Resource
import id.ergun.mymoviedb.util.eventbus.FavoriteEvent
import id.ergun.mymoviedb.util.gone
import id.ergun.mymoviedb.util.visible
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by alfacart on 21/10/20.
 */
@AndroidEntryPoint
class MovieFragment : Fragment() {

    companion object {
        private const val ARGUMENT_FAVORITE = "ARGUMENT_FAVORITE"
        fun newInstance(
            favorite: Boolean = false
        ): MovieFragment {
            val fragment = MovieFragment()
            val argument = Bundle()
            argument.putBoolean(ARGUMENT_FAVORITE, favorite)
            fragment.arguments = argument
            return fragment
        }
    }

    private lateinit var binding: MovieFragmentBinding

    private val movieViewModel by viewModels<MovieViewModel>()

    private lateinit var movieAdapter: MovieAdapter

    private var favoriteState: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventBus()
        loadArgument()
        initView()
        initAction()
        getMovies()
    }

    private fun initEventBus(){
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    private fun loadArgument() {
        if (arguments == null) return
        favoriteState = arguments?.getBoolean(ARGUMENT_FAVORITE, false) ?: false
        movieViewModel.setFavorite(favoriteState)
    }

    private fun initView() {
        movieAdapter = MovieAdapter()
        movieAdapter.favorite = favoriteState

        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun initAction() {
        binding.viewWarning.btnWarning.setOnClickListener {
            movieViewModel.refresh()
        }
    }

    private fun getMovies() {
        movieViewModel.getMovies().observe(requireActivity()) {
            movieAdapter.submitList(it)
        }

        movieViewModel.movieState.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> showData()
                Resource.Status.EMPTY_DATA -> showEmptyData(it.message.toString())
                Resource.Status.ERROR -> showWarning(it.message.toString())
            }
        }
    }

    private fun showLoading() {
        binding.wrapperContent.gone()
        binding.wrapperWarning.gone()
        binding.progressBar.visible()
    }

    private fun showData() {
        binding.wrapperContent.visible()
        binding.wrapperWarning.gone()
        binding.progressBar.gone()
    }

    private fun showEmptyData(message: String) {
        binding.wrapperContent.gone()
        binding.viewWarning.btnWarning.gone()
        binding.wrapperWarning.visible()
        binding.progressBar.gone()

        binding.viewWarning.tvWarning.text = message
    }

    private fun showWarning(message: String) {
        binding.wrapperContent.gone()
        binding.viewWarning.btnWarning.visible()
        binding.wrapperWarning.visible()
        binding.progressBar.gone()

        binding.viewWarning.tvWarning.text = message
    }

    @Subscribe
    fun onReceiveEventBus(event: FavoriteEvent) {
        if (event.type != Const.MOVIE_TYPE) return
        if (!event.changes) return
        if (!movieViewModel.favoritePage) return

        movieViewModel.refresh()
    }

    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}