package com.kodego.velascoben.fragmentdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kodego.velascoben.fragmentdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentOne = FragmentOne()
        val fragmentTwo = FragmentTwo()
        val fragmentThree = FragmentThree()

        // Initialize Fragment One to Load First
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentMain.id, fragmentOne)
            commit()
        }

        binding.btnFragment1.setOnClickListener() {
            supportFragmentManager.beginTransaction().apply {
                replace(binding.fragmentMain.id, fragmentOne)
                commit()
            }
        }

        binding.btnFragment2.setOnClickListener() {
            supportFragmentManager.beginTransaction().apply {
                replace(binding.fragmentMain.id, fragmentTwo)
                commit()
            }
        }

        binding.btnFragment3.setOnClickListener() {
            supportFragmentManager.beginTransaction().apply {
                replace(binding.fragmentMain.id, fragmentThree)
                commit()
            }
        }
    }
}