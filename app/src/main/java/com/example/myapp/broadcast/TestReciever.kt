package com.example.myapp.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TestReciever : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action == "ACTION_TEST") {
            println("Recieved Test Intent")
        }
    }

}