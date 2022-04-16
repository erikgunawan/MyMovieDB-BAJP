package id.ergun.mymoviedb.ui.view.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import id.ergun.mymoviedb.R
import id.ergun.mymoviedb.databinding.ReminderFragmentBinding
import id.ergun.mymoviedb.ui.view.home.HomeActivity
import id.ergun.mymoviedb.ui.viewmodel.reminder.ReminderViewModel
import id.ergun.mymoviedb.util.worker.ReminderWorker
import java.util.concurrent.TimeUnit

/**
 * Created by alfacart on 14/04/22.
 */
@AndroidEntryPoint
class ReminderFragment : Fragment() {

    private lateinit var binding: ReminderFragmentBinding

    private val reminderViewModel by viewModels<ReminderViewModel>()

    private val workManager by lazy {
        WorkManager.getInstance(requireContext())
    }

    companion object {
        const val DAILY_REMINDER_WORKER_NAME = "DailyReminderWorker"
    }

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

        reminderViewModel.getDailyReminder()

        reminderViewModel.isActiveDailyReminder.observe(requireActivity()) {
            binding.switchDaily.isChecked = it

            if (reminderViewModel.isActiveDailyReminderChanged) {
                if (binding.switchDaily.isChecked)
                    doWorker()
                else
                    cancelWorker()
            }
        }
    }

    private fun doWorker() {
        val worker = PeriodicWorkRequestBuilder<ReminderWorker>(
            24, TimeUnit.HOURS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            DAILY_REMINDER_WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            worker
        )
        Toast.makeText(requireContext(), "Daily reminder is activated", Toast.LENGTH_SHORT).show()
    }

    private fun initActions() {
        binding.switchDaily.let { switch ->
            switch.setOnClickListener {
                reminderViewModel.setDailyReminder(switch.isChecked)
            }
        }
    }

    private fun cancelWorker() {
        workManager.cancelUniqueWork(DAILY_REMINDER_WORKER_NAME)
        Toast.makeText(requireContext(), "Daily reminder is cancelled", Toast.LENGTH_SHORT).show()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val actionReminder = menu.findItem(R.id.reminderFragment)
        actionReminder?.isVisible = false

        val actionShare = menu.findItem(R.id.action_share)
        actionShare?.isVisible = false
    }
}