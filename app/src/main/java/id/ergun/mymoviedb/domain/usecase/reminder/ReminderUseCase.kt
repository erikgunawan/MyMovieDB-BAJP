package id.ergun.mymoviedb.domain.usecase.reminder

/**
 * Created by alfacart on 15/04/22.
 */
interface ReminderUseCase {
    fun setDailyReminder(active: Boolean)
    fun isActiveDailyReminder(): Boolean
}