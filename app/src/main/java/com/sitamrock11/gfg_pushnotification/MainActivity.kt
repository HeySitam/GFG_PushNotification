package com.sitamrock11.gfg_pushnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
     val CHANNEL_ID="GFG"
     val CHANNEL_NAME="GFG ContentWriting"
     val CHANNEL_DESCRIPTION="GFG NOTIFICATION"
     val link1="https://www.geeksforgeeks.org/"
     val link2="https://www.geeksforgeeks.org/contribute/"
     lateinit var et1:EditText
     lateinit var et2:EditText
     lateinit var btn1: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Binding Views by their IDs
        et1=findViewById(R.id.et1)
        et2=findViewById(R.id.et2)
        btn1=findViewById(R.id.btn1)
        btn1.setOnClickListener {
            //Converting the .png Image file to a Bitmap!
            val imgBitmap=BitmapFactory.decodeResource(resources,R.drawable.gfg_green)
            //Making intent1 to open the GFG home page
            val intent1=gfgOpenerIntent(link1)
            //Making intent2 to open The GFG contribution page
            val intent2=gfgOpenerIntent(link2)
            //Making pendingIntent1 to open the GFG home page after clicking the Notification
            val pendingIntent1=PendingIntent.getActivity(this,5,intent1,PendingIntent.FLAG_UPDATE_CURRENT)
            //Making pendingIntent2 to open the GFG contribution page after clicking the actionButton of the notification
            val pendingIntent2=PendingIntent.getActivity(this,6,intent2,PendingIntent.FLAG_UPDATE_CURRENT)
            //By invoking the notificationChannel() function we are registering our channel to the System
            notificationChannel()
            //Building the notification
            val nBuilder= NotificationCompat.Builder(this,CHANNEL_ID)
                    //adding notification Title
                    .setContentTitle(et1.text.toString())
                    //adding notification Text
                    .setContentText(et2.text.toString())
                    //adding notification SmallIcon
                    .setSmallIcon(R.drawable.notifications)
                    //adding notification Priority
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    //making the notification clickable
                    .setContentIntent(pendingIntent1)
                    .setAutoCancel(true)
                    //adding action button
                    .addAction(0,"LET CONTRIBUTE",pendingIntent2)
                    //adding largeIcon
                    .setLargeIcon(imgBitmap)
                    //making notification Expandable
                    .setStyle(NotificationCompat.BigPictureStyle()
                            .bigPicture(imgBitmap)
                            .bigLargeIcon(null))
                    .build()
            //finally notifying the notification
            val nManager = NotificationManagerCompat.from(this)
            nManager.notify(1, nBuilder)
        }
    }
    //Creating the notification channel
    private fun notificationChannel(){
        // check if the version is equal or greater than android oreo version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //creating notification channel and setting the description of the channel
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = CHANNEL_DESCRIPTION
            }
            //registering the channel to the System
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    //The function gfgOpenerIntent() returns an Implicit Intent to open a webpage
    private fun gfgOpenerIntent(link:String):Intent{
        val intent= Intent()
        intent.action=Intent.ACTION_VIEW
        intent.data=Uri.parse(link)
        return intent
    }
}
