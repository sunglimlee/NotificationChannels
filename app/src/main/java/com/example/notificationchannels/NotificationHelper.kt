package com.example.notificationchannels

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

//Helper Class가 왜 필요한걸까???
class NotificationHelper(base: Context?) : ContextWrapper(base) {
    //지금 계속 null값과 관련된 캐스팅에 문제가 계속 있네.. Kotlin이 이걸 강력하게 제한하고 있어서.. 그렇구나..
    //해결하자.. 이거 해결해야지 잘수있다.
    private var mManager : NotificationManager? = null

    companion object {
        const val CHANNEL1ID : String = "channel1ID"
        const val CHANNEL1NAME : String = "channel1Name"
        const val CHANNEL2ID : String = "channel2ID"
        const val CHANNEL2NAME : String = "channel2Name"
    }
    init {
        // Android API26이하일때 작업을 해주어야 하지...
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannels() {
        //자 일단 모든 Notification은 NotificationChannel을 사용해야 한다.
        //그리고 Noticaion은 NotificationManager에 의해서 관리가 된다. 그래서 Manager에 의해서 등급을 부여받겠지..
        val channel1 : NotificationChannel = NotificationChannel(CHANNEL1ID, CHANNEL1NAME, NotificationManager.IMPORTANCE_DEFAULT)
        //이제부터는 실제로 기능을 부여하는 부분이구나..
        channel1.enableLights(true)
        channel1.enableVibration(true)
        channel1.lightColor = Color.MAGENTA
        channel1.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        Log.e("nowlee", "메세지입니다.")
        //이부분에서 문제가 생기고 있다.
        getManager().createNotificationChannel(channel1)
        val channel2 : NotificationChannel = NotificationChannel(CHANNEL2ID, CHANNEL2NAME, NotificationManager.IMPORTANCE_DEFAULT)
        //이제부터는 실제로 기능을 부여하는 부분이구나..
        channel2.enableLights(true)
        channel2.enableVibration(true)
        channel2.lightColor = Color.MAGENTA
        channel2.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        getManager().createNotificationChannel(channel2)
    }

    public fun getManager() : NotificationManager {
        if (mManager == null) {
            //getSystemService를 통해서 NotificationManager를 받을 수 있구나.
                //이부분 정말 중요하다.
            mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return mManager as NotificationManager
    }
    public fun getChannel1Notification(title : String, message : String) : NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, CHANNEL1ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_one)
    }
    public fun getChannel2Notification(title : String, message : String) : Notification.Builder {
        return Notification.Builder(applicationContext, CHANNEL2ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_two)
    }


}