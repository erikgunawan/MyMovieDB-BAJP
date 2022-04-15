package id.ergun.mymoviedb.ui.view.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.ergun.mymoviedb.R
import id.ergun.mymoviedb.databinding.ReminderFragmentBinding
import id.ergun.mymoviedb.ui.view.home.HomeActivity
import id.ergun.mymoviedb.ui.viewmodel.reminder.ReminderViewModel
import id.ergun.mymoviedb.util.NotificationHelper

/**
 * Created by alfacart on 14/04/22.
 */
@AndroidEntryPoint
class ReminderFragment : Fragment() {

    private lateinit var binding: ReminderFragmentBinding

    private val reminderViewModel by viewModels<ReminderViewModel>()

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
        initActions()

        reminderViewModel.getDailyReminder().observe(requireActivity()) {
            binding.switchDaily.isChecked = it
//
//            if (reminderViewModel.isActiveDailyReminderChanged) {
//            }
        }
    }

    private fun initActions() {
        binding.switchDaily.setOnClickListener {
            NotificationHelper(requireContext()).createNotification()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val actionReminder = menu.findItem(R.id.reminderFragment)
        actionReminder?.isVisible = false

        val actionShare = menu.findItem(R.id.action_share)
        actionShare?.isVisible = false
    }
}