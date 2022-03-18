package com.example.notificationchannels

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.app.NotificationCompat
import java.security.spec.MGF1ParameterSpec

class MainActivity : AppCompatActivity() {
    private lateinit var editTextTitle : EditText
    private lateinit var editTextMessage : EditText
    private lateinit var buttonChannel1 : Button
    private lateinit var buttonChannel2 : Button
    private lateinit var mNotificationHelper: NotificationHelper
    private var listener : View.OnClickListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        memberVariableInit()

    }

    private fun memberVariableInit() {
        editTextMessage = findViewById<EditText>(R.id.edittext_message)
        editTextTitle = findViewById<EditText>(R.id.edittext_title)
        buttonChannel1 = findViewById<Button>(R.id.button_channel1)
        buttonChannel2 = findViewById<Button>(R.id.button_channel2)
        buttonsOnClickListener()
        listener = View.OnClickListener {
            when (it.id) {
                R.id.button_channel1 -> sendOnChannel1(editTextTitle.text.toString(), editTextMessage.text.toString())
                R.id.button_channel2 -> sendOnChannel2(editTextTitle.text.toString(), editTextMessage.text.toString())
            }
        }
        buttonChannel1.setOnClickListener(listener)
        buttonChannel2.setOnClickListener(listener)
        mNotificationHelper = NotificationHelper(applicationContext)

    }
    private fun buttonsOnClickListener() {
    }

    private fun sendOnChannel1(title : String, message : String) {
        var nb : NotificationCompat.Builder = mNotificationHelper.getChannel1Notification(title,message)
        //여기 외부에서 연결시켜주는구나.. 비로서 헬퍼클래스에서 만든 모든 것들을 연결시키기 시작하는데..
        //일단 NotificationCompat.Builder 클래스를 만든 인스턴스 변수를 가지고있고..
        //getManager를 통해서 NotificationManager를 받아서 그 메이저에게 Notifiy하는데 id : 1이고 builder클래스를
        //build 하라는 거구나 결국 메니저가 하는거지.. 뭐에 의해서 notify에 의해서
        mNotificationHelper.getManager().notify(1, nb.build())

    }

    private fun sendOnChannel2(title : String, message : String) {
        var nb : Notification.Builder = mNotificationHelper.getChannel2Notification(title,message)
        mNotificationHelper.getManager().notify(2, nb.build())
    }

}