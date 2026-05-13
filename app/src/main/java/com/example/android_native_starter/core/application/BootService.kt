package com.example.android_native_starter.core.application

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class BootReceiver: BroadcastReceiver() {
    override fun onReceive(ctx: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED || intent?.action == "com.example.TEST_BOOT") {
            Log.d("Android_Native_Starter", "Device boot completed")

            Toast.makeText(ctx, "Sistem Android Berhasil Booting!", Toast.LENGTH_LONG).show()

            val serviceIntent = Intent(ctx, BootService::class.java)

            ctx?.startService(serviceIntent)
        }
    }
}

class BootService : Service() {

    override fun onCreate() {
        super.onCreate()

        Log.d("BOOT_SERVICE", "Service Created")
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        Log.d("BOOT_SERVICE", "Service Started After Boot")

        // contoh kerja background
        Thread {
            while (true) {
                Log.d("BOOT_SERVICE", "Running...")

                Thread.sleep(5000)
            }
        }.start()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}