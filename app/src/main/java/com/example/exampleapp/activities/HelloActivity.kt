package com.example.exampleapp.activities

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.exampleapp.ExampleApplication
import com.example.exampleapp.R
import com.example.exampleapp.receiver.NumberReceiver
import com.example.exampleapp.receiver.NumberReceiver.Companion.NUMBER_RECEIVER_ACTION
import com.example.exampleapp.room.User
import com.example.exampleapp.services.SimpleService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HelloActivity : AppCompatActivity() {

    companion object {
        val TAG = HelloActivity.javaClass.canonicalName
        const val USER_NAME_EXTRA = "USER_NAME"
        val numberReceiver = NumberReceiver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        val message = intent!!.getStringExtra(USER_NAME_EXTRA)
        findViewById<TextView>(R.id.myUserName).text = message
        registerReceiver(numberReceiver, IntentFilter(NUMBER_RECEIVER_ACTION))
    }

    fun onStartServiceBtnClick(view: View) {
        val intent = Intent(this, SimpleService::class.java)
        intent.putExtra(NumberReceiver.USER_NAME_EXTRA, findViewById<TextView>(R.id.myUserName).text)
        startService(intent)
    }

    fun onStopServiceBtnClick(view: View) {
        val intent = Intent(this, SimpleService::class.java)
        stopService(intent)
    }

    fun onReadUsersBtnClick(v :View){
        GlobalScope.launch {
          val userList = (applicationContext as ExampleApplication).database.userDao().getAll()
            for(user in userList){
                val line = "ID: ${user.uid}. User: ${user.userName}. Number: ${user.number} \n"
                Log.d(TAG, line)
            }
            findViewById<TextView>(R.id.usersList).text = userList.size.toString()
        }
    }

    override fun onDestroy() {
        unregisterReceiver(numberReceiver)
        super.onDestroy()
    }

}