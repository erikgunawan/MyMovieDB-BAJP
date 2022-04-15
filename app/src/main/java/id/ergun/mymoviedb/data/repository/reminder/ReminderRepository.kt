package id.ergun.mymoviedb.data.repository.reminder

/**
 * Created by alfacart on 15/04/22.
 */
interface ReminderRepository {
    suspend fun setDailyReminder(active: Boolean)
    suspend fun isActiveDailyReminder(): Boolean
}