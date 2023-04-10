package com.example.alarm.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.PowerManager
import com.example.alarm.AlarmActivatedActivity
import com.example.alarm.utills.MyData

class MyReceiver : BroadcastReceiver() {

    private val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    private lateinit var ringtone: Ringtone
    override fun onReceive(context: Context, intent: Intent) {
        // Set up the MediaPlayer to play the default ringtone
        when (intent.action) {
            "START_RINGTONE" -> {
                val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                val wakeLock = powerManager.newWakeLock(
                    PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                    "MyApp:MyWakeLock"
                )
                wakeLock.acquire(10 * 60 * 1000L /*10minutes*/)

                ringtone = RingtoneManager.getRingtone(context, ringtoneUri)
                ringtone.play()
                MyData.ringtone = ringtone
                //going to alarm Activated Activity..
                context.startActivity(Intent(
                    context,
                    AlarmActivatedActivity::class.java
                ).addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK))
            }
            "DISMISS_ALARM" -> {
                MyData.ringtone?.stop()
            }
        }
    }
}