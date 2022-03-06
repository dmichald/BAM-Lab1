package com.example.exampleapp.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.exampleapp.activities.HelloActivity
import com.example.exampleapp.receiver.NumberReceiver
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

class SimpleService : Service() {
    private var isDestroyed = false;
    private var userName = ""
    private var stoppedAt = 0
    companion object {
        val TAG = SimpleService.javaClass.canonicalName
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service created")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        GlobalScope.launch {
            var number = 0
            userName = intent!!.getStringExtra(NumberReceiver.USER_NAME_EXTRA)
            sendToNumberReceiver(number, userName)
            while (!isDestroyed) {
                number++
                stoppedAt = number
                Log.d("$TAG startId:", "New number $number")
                delay(1000)
            }
        }


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        sendToNumberReceiver(stoppedAt, userName)
        isDestroyed = true
        Log.d(TAG, "Service stopped")
        super.onDestroy()
    }

    private fun sendToNumberReceiver(number: Int, userName : String){
        val newIntent = Intent(NumberReceiver.NUMBER_RECEIVER_ACTION)
        newIntent.putExtra(NumberReceiver.NUMBER_EXTRA, number)
        newIntent.putExtra(NumberReceiver.USER_NAME_EXTRA, userName)
        sendBroadcast(newIntent)
    }

}