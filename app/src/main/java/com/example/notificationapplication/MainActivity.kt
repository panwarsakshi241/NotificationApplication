package com.example.notificationapplication

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tapadoo.alerter.Alerter

class MainActivity : AppCompatActivity() {

    lateinit var notificationbtn: Button
    lateinit var editText: EditText

    private val CHANNEL_ID = "channel_id_01"
    private val notificationId = 101

    companion object {
        var otp = 1000
        var name = "One Time Password : ${otp} "
        var descriptionText = "Dear Customer your four digit one time password is : {$otp} "
    }

    /**
     *
     * for realtime application use
     * random number  to generate
     * the four digit otp .
     *
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationbtn = findViewById(R.id.sendnotification)

        createNotificationChannel()


        notificationbtn.setOnClickListener {
            sendNotification()


            //open a dialog message to enter the otp

            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.verify_layout, null)
            editText = dialogLayout.findViewById(R.id.otpVerificationET) as EditText


            with(builder) {
                setTitle("Enter four digit OTP ")
                setPositiveButton("Verify") { dialog, which ->

                    verifyOtp()

//                   //generate the alert notification
//
//                    alertNotification()
//
//                    Intent(this@MainActivity,SecondActivity::class.java).also{
//                       startActivity(it)
//                    }
//
//                    Toast.makeText(this@MainActivity, "verify button clicked!", Toast.LENGTH_SHORT)
//                        .show()
//                    //compare the enteres otp and sent otp
                }
                setNegativeButton("Send Again") { dialog, which ->
                    //resend the notification by increasing the value of otp
                    otp+=1

                    sendNotification()

//                    Toast.makeText(
//                        this@MainActivity,
//                        "Send Again button clicked!",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }

                setView(dialogLayout)
                show()
            }


        }

    }

    //Create the notification

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            name = "One Time Password : ${otp} "
            descriptionText = "One Time Password : ${otp}"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }


    }
    //sending notification

    private fun sendNotification() {

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val bitmap = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.ic_baseline_android_24
        )
        val bitmapLargeIcon =
            BitmapFactory.decodeResource(applicationContext.resources, R.drawable.image)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_message)
            .setContentTitle(name)
            .setContentText(descriptionText)
            .setLargeIcon(bitmapLargeIcon)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Hello dear customer ,Thank you for signing up in Notification App. \nHere is your Verification code for the sign up procedure .\nVerification code ${otp}")
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }


    }


    //generate alert ntoification

    private fun alertNotification() {

        Alerter.Companion.create(this)
            .setTitle("OTP Verified")
            .setText("Thank You for Your Support !")
            .setIcon(R.drawable.ic_message)
            .setBackgroundColorRes(R.color.red)
            .setDuration(6000)
            .setOnClickListener(View.OnClickListener {
                Toast.makeText(applicationContext, "Alert Clicked !", Toast.LENGTH_SHORT).show()
                Intent(this@MainActivity , SecondActivity::class.java).also {
                    startActivity(it)
                }
            }).show()

        Toast.makeText(applicationContext, "Alert Notification Dialog !", Toast.LENGTH_SHORT).show()
    }

    // verify otp
    private fun verifyOtp(){
        val enteredOTP = editText.text.toString()

        if(otp.toString() == enteredOTP){

            Toast.makeText(this,"Verified ! ",Toast.LENGTH_SHORT).show()
            alertNotification()
//            Intent(this@MainActivity , SecondActivity::class.java).also {
//                startActivity(it)
//            }

        }else{
            Toast.makeText(this,"Incorrect OTP !",Toast.LENGTH_SHORT).show()
        }
    }
}