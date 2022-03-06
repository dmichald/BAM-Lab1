package com.example.exampleapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.exampleapp.ExampleApplication
import com.example.exampleapp.room.AppDatabase
import com.example.exampleapp.room.User
import com.example.exampleapp.services.SimpleService
//import com.example.exampleapp.room.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NumberReceiver : BroadcastReceiver() {
    companion object {
        const val NUMBER_EXTRA = "number"
        const val USER_NAME_EXTRA = "userName"
        const val NUMBER_RECEIVER_ACTION = "NUMBER_RECEIVER_ACTION"
        val TAG = NumberReceiver.javaClass.canonicalName
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val number = intent?.getIntExtra(NUMBER_EXTRA, 0) ?: 0
        val user = intent?.getStringExtra(USER_NAME_EXTRA) ?: ""
        Log.d(TAG, "User: $user. Number: $number.")

        GlobalScope.launch {
            (context!!.applicationContext as
                    ExampleApplication).database.userDao().insert(
                User(userName = user, number = number)
            )
        }
    }

}