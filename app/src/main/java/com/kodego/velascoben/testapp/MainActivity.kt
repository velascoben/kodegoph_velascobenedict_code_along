package com.kodego.velascoben.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.kodego.velascoben.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
//            Toast.makeText(applicationContext,"Hello from Toast",Toast.LENGTH_LONG).show()
            var name : String = "Welcome Back, " + binding.etName.text.toString() + "!"
            binding.txtName.text =  name

            var item : String = binding.spinnerID.selectedItem.toString()
            Toast.makeText(applicationContext,item, Toast.LENGTH_LONG).show()
        }

        binding.rgRadioGroup.setOnCheckedChangeListener { radioGroup, checkedOption ->
            when(checkedOption) {
                R.id.rb1 -> Toast.makeText(applicationContext,"Option1 Selected", Toast.LENGTH_LONG).show()
                R.id.rb2 -> Toast.makeText(applicationContext,"Option2 Selected", Toast.LENGTH_LONG).show()
                R.id.rb3 -> Toast.makeText(applicationContext,"Option3 Selected", Toast.LENGTH_LONG).show()
            }
        }

        binding.cb1.setOnClickListener() {
            if(binding.cb1.isChecked) {
                Toast.makeText(applicationContext,"Check Box 1 Checked", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext,"Check Box 2 Checked", Toast.LENGTH_LONG).show()
            }
        }

        val data = arrayListOf<String>("Option 1", "Option 2", "Option 3")
        val adapterParent = ArrayAdapter(applicationContext,R.layout.textview_xml,data)

        binding.spinnerID.adapter = adapterParent


        binding.switchTest.setOnClickListener() {
            if(binding.switchTest.isChecked) {
                Toast.makeText(applicationContext,"Switch is ON", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext,"Switch is OFF", Toast.LENGTH_LONG).show()
            }
        }

    }
}