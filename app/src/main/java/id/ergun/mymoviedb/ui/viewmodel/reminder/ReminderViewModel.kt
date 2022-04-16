package id.ergun.mymoviedb.ui.viewmodel.reminder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ergun.mymoviedb.domain.usecase.reminder.ReminderUseCase
import javax.inject.Inject

/**
 * Created by alfacart on 15/04/22.
 */
@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val useCase: ReminderUseCase
) : ViewModel() {

    var isActiveDailyReminder: MutableLiveData<Boolean> = MutableLiveData()
    var isActiveDailyReminderChanged: Boolean = false

    fun getDailyReminder() {
        isActiveDailyReminder.value = useCase.isActiveDailyReminder()
    }

    fun setDailyReminder(active: Boolean) {
        useCase.setDailyReminder(active)
        isActiveDailyReminderChanged = true
        isActiveDailyReminder.value = active
    }
}