package id.ergun.mymoviedb.util.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import id.ergun.mymoviedb.util.NotificationHelper

/**
 * Created by alfacart on 15/04/22.
 */
class ReminderWorker(val context: Context, val params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        NotificationHelper(context).createNotification()
        return Result.success()
    }
}