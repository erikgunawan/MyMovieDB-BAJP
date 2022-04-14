package id.ergun.mymoviedb.ui.view.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.ergun.mymoviedb.R
import id.ergun.mymoviedb.databinding.ReminderFragmentBinding
import id.ergun.mymoviedb.ui.view.home.HomeActivity

/**
 * Created by alfacart on 14/04/22.
 */
@AndroidEntryPoint
class ReminderFragment : Fragment() {

    private lateinit var binding: ReminderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as HomeActivity).supportActionBar?.title = ""
        binding = ReminderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.reminderFragment)
        item?.isVisible = false
    }
}