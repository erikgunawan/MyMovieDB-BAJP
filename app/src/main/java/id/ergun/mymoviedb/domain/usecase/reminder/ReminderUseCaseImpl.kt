package id.ergun.mymoviedb.domain.usecase.reminder

import id.ergun.mymoviedb.data.repository.reminder.ReminderRepository
import javax.inject.Inject

/**
 * Created by alfacart on 15/04/22.
 */
class ReminderUseCaseImpl @Inject constructor(private val repository: ReminderRepository) : ReminderUseCase {
    override fun setDailyReminder(active: Boolean) {
        repository.setDailyReminder(active)
    }

    override fun isActiveDailyReminder(): Boolean {
        return repository.isActiveDailyReminder()
    }
}