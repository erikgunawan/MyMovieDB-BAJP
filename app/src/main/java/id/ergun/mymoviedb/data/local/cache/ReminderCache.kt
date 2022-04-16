package id.ergun.mymoviedb.data.local.cache

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Created by alfacart on 15/04/22.
 */
class ReminderCache @Inject constructor(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREF_NAME = "REMINDER_PREFERENCE"
        const val PREF_DAILY_REMINDER = "PREF_DAILY_REMINDER"
    }

    fun setDailyReminder(status: Boolean) {
        preferences.edit()
            .putBoolean(PREF_DAILY_REMINDER, status)
            .apply()
    }

    fun isActiveDailyReminder(): Boolean {
        return preferences
            .getBoolean(PREF_DAILY_REMINDER, false)
    }
}