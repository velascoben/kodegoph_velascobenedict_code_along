package com.kodego.velascoben.sqliteexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kodego.velascoben.sqliteexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var databaseHelper = DatabaseHelper(applicationContext)
        var everyEmployee : MutableList<EmployeeModel> = databaseHelper.getAllData()
        var adapter = EmployeeAdapter(everyEmployee)
        adapter.onItemDelete = { item : EmployeeModel, position : Int ->

            // Delete from Table
            var databaseHelper = DatabaseHelper(applicationContext)
            databaseHelper.deleteOne(item)

            // Delete from Recycler View
            adapter.employeeModel.removeAt(position)
            adapter.notifyDataSetChanged()
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnSave.setOnClickListener() {
            lateinit var employeeModel: EmployeeModel

            try{
                var name = binding.etName.text.toString()
                var salary = binding.etSalary.text.toString().toInt()

                // Creates New Employee Object
                employeeModel = EmployeeModel( -1,name,salary)

                // Adds New Employee to Recycler View
                adapter.employeeModel.add(employeeModel)
                adapter.notifyDataSetChanged()

                // Adds New Employee to Database
                var databaseHelper = DatabaseHelper(applicationContext)
                databaseHelper.addOne(employeeModel)
                Toast.makeText(applicationContext,"Success",Toast.LENGTH_LONG).show()
            } catch (ex: Exception) {
                Toast.makeText(applicationContext,"Error Input",Toast.LENGTH_LONG).show()
            }



        }

    }

}