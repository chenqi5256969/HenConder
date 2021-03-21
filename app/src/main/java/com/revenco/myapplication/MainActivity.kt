package com.revenco.myapplication

import android.content.Intent
import android.os.*
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.JobIntentService
import androidx.databinding.DataBindingUtil
import com.revenco.myapplication.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var toast: Toast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        contentView.oneBtn.setOnClickListener {
            thread {
                Looper.prepare()
                toast = Toast.makeText(this, "thread", Toast.LENGTH_SHORT)
                toast.show()
                Looper.loop()
            }
        }
        contentView.twoBtn.setOnClickListener {
            toast.setText("main")
            toast.show()
        }


        val handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                log(msg.obj.toString())
            }
        }

        val frontMessage = Message.obtain()
        frontMessage.obj = "frontMessage"
        val normalMessage = Message.obtain()
        normalMessage.obj = "normalMessage"
        log("time-->${SystemClock.uptimeMillis()}")

        handler.sendMessage(normalMessage)
        handler.sendMessageAtFrontOfQueue(frontMessage)

        handler.post {
            HandlerThread("one")

        }

    }

    fun click(view: View) {

    }


    class InnerService : JobIntentService() {
        override fun onHandleWork(intent: Intent) {
            TODO("Not yet implemented")
        }


    }

}