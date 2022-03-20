package com.example.notificationchannels

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {
    private lateinit var editTextTitle : EditText
    private lateinit var editTextMessage : EditText
    private lateinit var buttonChannel1 : Button
    private lateinit var buttonChannel2 : Button
    private lateinit var mNotificationHelper: NotificationHelper
    private lateinit var listener : View.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //이 이후에 나와야지 View를 사용할 수 있는거지..
        initMemberVariables()

    }

    private fun initMemberVariables() {
        editTextMessage = findViewById<EditText>(R.id.edittext_message)
        editTextTitle = findViewById<EditText>(R.id.edittext_title)
        buttonChannel1 = findViewById<Button>(R.id.button_channel1)
        buttonChannel2 = findViewById<Button>(R.id.button_channel2)
        buttonsOnClickListener()
        //순서는 바뀌면 안된다는 것..
        buttonChannel1.setOnClickListener(listener)
        buttonChannel2.setOnClickListener(listener)
        mNotificationHelper = NotificationHelper(applicationContext)

    }
    private fun buttonsOnClickListener() {
        listener = View.OnClickListener {
            when (it.id) {
                R.id.button_channel1 -> sendOnChannel1(editTextTitle.text.toString(), editTextMessage.text.toString())
                R.id.button_channel2 -> sendOnChannel2(editTextTitle.text.toString(), editTextMessage.text.toString())
            }
        }
    }

    private fun sendOnChannel1(title : String, message : String) {
        var nb : NotificationCompat.Builder = mNotificationHelper.getChannel1Notification(title,message)
        //여기 외부에서 연결시켜주는구나.. 비로서 헬퍼클래스에서 만든 모든 것들을 연결시키기 시작하는데..
        //일단 NotificationCompat.Builder 클래스를 만든 인스턴스 변수를 가지고있고..
        //mNotificationHelper클래서에 있는 getChannel1Notification을 실행할 때
        //이 안에서 어떤일이 일어나는지는 모르지만 분명히 CHANNELID1을 통해서 mManager에 대한
        //Manager가 정해질거고 그리고 그 CHANNELID1에 해당하는 NotificaionChannel을
        //안드로이드 노티피케이션 메니저가 관리 할 수 있도록 한다.
        //notify의 id는 화면뿌려주는 기능을 할때 똑같은 아이디가 있으면 리플레이스를 해준다.
        mNotificationHelper.getManager().notify(1, nb.build())
        //위의 내용이 결국 화면에 뿌려주는 기능인데 이름을 잘봐라. send on channel1이잖아. ㅋ
    }

    private fun sendOnChannel2(title : String, message : String) {
        var nb : Notification.Builder = mNotificationHelper.getChannel2Notification(title,message)
        mNotificationHelper.getManager().notify(2, nb.build())

        //그러면 클릭은 어디서 연결할 수 있을까? 상식적으로 클릭은 Vew에서 setOnClickListener를 하는건데..
        //화면에 뿌려주는 부분의 Context는 이제 NotificationManager에서 하지 않을까?
        //그러면 클릭리스너도 Manager에서 관리해줘야하지 않을까?
        //어디서 해야할까? 모르겠다.
    }

}