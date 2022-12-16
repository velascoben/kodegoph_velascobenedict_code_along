package com.kodego.velascoben.notificationdemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.kodego.velascoben.notificationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = NotificationService(applicationContext)
        binding.btnNotif.setOnClickListener() {
            service.showNotification()
        }

//        binding.btnSnackBar.setOnClickListener() {
//            Snackbar.make(it, "Button Clicked", Snackbar.LENGTH_LONG)
//                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
//                .setBackgroundTint(Color.parseColor("#006400"))
//                .setAction("Action") {
//                    Toast.makeText(applicationContext,"Snackbar button pressed",Toast.LENGTH_SHORT ).show()
//                }.show()
//        }
    }
}