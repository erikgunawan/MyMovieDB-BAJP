package id.ergun.mymoviedb.ui.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.ergun.mymoviedb.R
import id.ergun.mymoviedb.databinding.FavoriteFragmentBinding
import id.ergun.mymoviedb.ui.view.home.HomePagerAdapter
import id.ergun.mymoviedb.util.autoCleared

/**
 * Created by alfacart on 26/11/20.
 */
@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    private var binding: FavoriteFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initView()
    }

    private fun initView() {
        val sectionsPagerAdapter = HomePagerAdapter(requireContext(), childFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val actionShare = menu.findItem(R.id.action_share)
        actionShare?.isVisible = false
    }
}