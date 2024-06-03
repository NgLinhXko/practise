package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.example.myapplication.service.MessageService

class TestActivity : AppCompatActivity() {
    private var messenger: Messenger? = null
    private var isBound = false
    private lateinit var tvResult: AppCompatTextView
    private lateinit var btnResult: AppCompatButton

    private val replyHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MessageService.MSG_REPLY -> {
                    val result = msg.data.getInt("result")
                    tvResult.text = "Result: $result"
                    println("Result: $result")
                }

                else -> super.handleMessage(msg)
            }
        }
    }
    private val replyMessenger = Messenger(replyHandler)

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            messenger = Messenger(service)
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            messenger = null
            isBound = false
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResult = findViewById(R.id.tvMain)
        btnResult = findViewById(R.id.btnMain)
        btnResult.setOnClickListener {
            val a = 10
            val b = 5
            sendMessage(a, b)
        }
        Intent(this, MessageService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

    private fun sendMessage(a: Int, b: Int) {
        if (isBound) {
            val msg = Message.obtain(null, MessageService.MSG_ADD)
            val bundle = Bundle()
            bundle.putInt("a", a)
            bundle.putInt("b", b)
            msg.data = bundle
            msg.replyTo = replyMessenger

            try {
                messenger?.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
}
