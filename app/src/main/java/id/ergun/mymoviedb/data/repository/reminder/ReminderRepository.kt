package id.ergun.mymoviedb.data.repository.reminder

/**
 * Created by alfacart on 15/04/22.
 */
interface ReminderRepository {
    fun setDailyReminder(active: Boolean)
    fun isActiveDailyReminder(): Boolean
}