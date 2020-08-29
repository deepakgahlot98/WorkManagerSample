package com.gahlot.workmanagersmaple

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class SampleWorker(
    context: Context,
    workerParams: WorkerParameters
    ) : Worker(context,workerParams) {

    private val TAG = "SampleWorker"
    val FIRST_KEY = "result"
    var result = 1

    override fun doWork(): Result {
        val tableNumber = inputData.getInt("number",10)
//        for (i in 1..10) {
//            result = tableNumber * i
//            TimeUnit.SECONDS.sleep(1)
            createNotification("Charge Battery to ", result)
//        }
        return Result.retry()
    }

    fun createNotification(title: String, description: Int) {

        var notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel("101", "channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, "101")
            .setContentTitle(title)
            .setContentText(description.toString())
            .setSmallIcon(R.drawable.ic_launcher_background)

        notificationManager.notify(1, notificationBuilder.build())

    }

}
