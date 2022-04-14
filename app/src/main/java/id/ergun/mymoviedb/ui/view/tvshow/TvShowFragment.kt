package id.ergun.mymoviedb.ui.view.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.ergun.mymoviedb.databinding.TvShowFragmentBinding
import id.ergun.mymoviedb.ui.viewmodel.tvshow.TvShowViewModel
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
class TvShowFragment : Fragment() {

    companion object {
        private const val ARGUMENT_FAVORITE = "ARGUMENT_FAVORITE"
        fun newInstance(
            favorite: Boolean = false
        ): TvShowFragment {
            val fragment = TvShowFragment()
            val argument = Bundle()
            argument.putBoolean(ARGUMENT_FAVORITE, favorite)
            fragment.arguments = argument
            return fragment
        }
    }

    private lateinit var binding: TvShowFragmentBinding

    private val tvShowViewModel by viewModels<TvShowViewModel>()

    private lateinit var tvShowAdapter: TvShowAdapter

    private var favoriteState: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TvShowFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventBus()
        loadArgument()
        initView()
        initAction()
        getTvShows()
    }

    private fun initEventBus(){
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    private fun loadArgument() {
        if (arguments == null) return
        favoriteState = arguments?.getBoolean(ARGUMENT_FAVORITE, false) ?: false
        tvShowViewModel.setFavorite(favoriteState)
    }

    private fun initView() {
        tvShowAdapter = TvShowAdapter()
        tvShowAdapter.favorite = favoriteState

        with(binding.rvTvShow) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
    }

    private fun initAction() {
        binding.viewWarning.btnWarning.setOnClickListener {
            tvShowViewModel.refresh()
        }
    }

    private fun getTvShows() {
        tvShowViewModel.getTvShows().observe(requireActivity()) {
            tvShowAdapter.submitList(it)
        }

        tvShowViewModel.tvShowState.observe(requireActivity()) {
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
        if (event.type != Const.TV_SHOW_TYPE) return
        if (!event.changes) return
        if (!tvShowViewModel.favoritePage) return

        tvShowViewModel.refresh()
    }

    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}