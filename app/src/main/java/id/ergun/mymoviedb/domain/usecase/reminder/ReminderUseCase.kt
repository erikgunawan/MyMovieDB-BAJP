package id.ergun.mymoviedb.domain.usecase.reminder

/**
 * Created by alfacart on 15/04/22.
 */
interface ReminderUseCase {
    suspend fun setDailyReminder(active: Boolean)
    suspend fun isActiveDailyReminder(): Boolean
}