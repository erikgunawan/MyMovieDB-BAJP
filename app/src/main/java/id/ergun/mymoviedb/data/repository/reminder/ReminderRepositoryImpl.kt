package id.ergun.mymoviedb.data.repository.reminder

import id.ergun.mymoviedb.data.local.cache.ReminderCache
import javax.inject.Inject

/**
 * Created by alfacart on 15/04/22.
 */
class ReminderRepositoryImpl @Inject constructor(private val data: ReminderCache) :
    ReminderRepository {
    override fun setDailyReminder(active: Boolean) {
        data.setDailyReminder(active)
    }

    override fun isActiveDailyReminder(): Boolean {
        return data.isActiveDailyReminder()
    }
}