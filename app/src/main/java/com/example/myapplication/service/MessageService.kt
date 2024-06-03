package com.example.myapplication.service

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException

class MessageService : Service() {
    companion object {
        const val MSG_ADD = 1
        const val MSG_REPLY = 2
    }

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_ADD -> {
                    val a = msg.data.getInt("a")
                    val b = msg.data.getInt("b")
                    val result = a + b

                    val reply = Message.obtain(null, MSG_REPLY)
                    val bundle = Bundle()
                    bundle.putInt("result", result)
                    reply.data = bundle
                    try {
                        msg.replyTo.send(reply)
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }

                else -> super.handleMessage(msg)
            }
        }
    }
    private val messenger = Messenger(handler)
    override fun onBind(p0: Intent?): IBinder? {
        return messenger.binder
    }
}

//class MyService1 : Service() {
//    override fun onCreate() {
//        super.onCreate()
//        // Khởi tạo các tài nguyên cần thiết
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        // Thực hiện tác vụ của Service
//        // ...
//        return START_STICKY // Hoặc START_NOT_STICKY, START_REDELIVER_INTENT
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        // Giải phóng các tài nguyên
//    }
//}
