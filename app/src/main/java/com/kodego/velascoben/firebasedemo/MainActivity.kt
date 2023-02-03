package com.kodego.velascoben.firebasedemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kodego.velascoben.firebasedemo.databinding.ActivityMainBinding
import com.kodego.velascoben.firebasedemo.databinding.UpdateDialogBinding
import com.kodego.velascoben.firebasedemo.db.Employee
import com.kodego.velascoben.firebasedemo.db.EmployeeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var adapter : EmployeeAdapter

    var dao = EmployeeDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        view()

        binding.btnSave.setOnClickListener() {
            var name = binding.etName.text.toString()
            var salary = binding.etSalary.text.toString()

            dao.get().addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var employees : ArrayList<Employee> = ArrayList<Employee>()

                    var dataFromDB = snapshot.children

                    for (data in dataFromDB) {

                        var dataName = data.child("name").value.toString()

                        if(name == dataName) {

                            Toast.makeText(applicationContext,"Employee already exists",Toast.LENGTH_LONG).show()

                        } else {

                            dao.add(Employee("", name, salary))
                            adapter.notifyDataSetChanged()
                            binding.etName.setText("")
                            binding.etSalary.setText("")
                            binding.etName.requestFocus()
                            displayMessage("New Employee Added")
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


        }

    }

    private fun view() {

        dao.get().addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var employees : ArrayList<Employee> = ArrayList<Employee>()

                var dataFromDB = snapshot.children

                for (data in dataFromDB) {

                    // Get ID of Object from DB
                    var id = data.key.toString()

                    var name = data.child("name").value.toString()
                    var salary = data.child("salary").value.toString()

                    var employee = Employee(id,name,salary)
                    employees.add(employee)
                }

                adapter = EmployeeAdapter(employees)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)


                adapter.onItemDelete = {
                        item : Employee, position : Int ->

                    delete(item)
                    adapter.employeeModel.removeAt(position)
                    adapter.notifyDataSetChanged()
                    displayMessage("Employee Deleted")
                }

                adapter.onUpdate = {
                        item : Employee, position : Int ->

                    showUpdateDialog(item.name,item.salary, item.id)
                    adapter.notifyDataSetChanged()

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun updateData(name : String, salary : String, id : String) {
        var mapData = mutableMapOf<String,String>()
        mapData["name"] = name
        mapData["salary"] = salary
        dao.update(id,mapData)
    }

    private fun delete(item: Employee) {
        dao.remove(item.id)
        view()
    }

    fun showUpdateDialog (name : String, salary : String, id : String) {
        val dialog = MaterialAlertDialogBuilder(this)
//        val binding : UpdateDialogBinding = UpdateDialogBinding.inflate(layoutInflater)
        val binding : UpdateDialogBinding = UpdateDialogBinding.inflate(layoutInflater)
//        dialog.setContentView(binding.root)
        dialog.setView(binding.root)
//        dialog.show()
            .setTitle("UPDATE EMPLOYEE DETAILS")
            .setMessage("Enter changes in employee data")
            .setPositiveButton("Update") { dialog, _ ->
                var newName = binding.etNewName.text.toString()
                var newSalary = binding.etNewSalary.text.toString()

                GlobalScope.launch(Dispatchers.IO) {

                    if (newName == "") {
                        newName = name
                    }

                    if (newSalary == "") {
                        newSalary = salary.toString()
                    }

                    updateData(newName, newSalary, id)
                    displayMessage("Employee Updated")
                    view()
                }

                adapter.notifyDataSetChanged()

                /**
                 * Do as you wish with the data here --
                 * Download/Clone the repo from my Github to see the entire implementation
                 * using the link provided at the end of the article.
                 */

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                displayMessage("Operation Cancelled")
                dialog.dismiss()
            }
            .show()
    }

    private fun displayMessage(message : String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .setBackgroundTint(Color.parseColor("#499C54"))
            .setActionTextColor(Color.parseColor("#FFFFFF"))
            .setAction("DISMISS") {
//                Toast.makeText(applicationContext,"Snackbar clicked",Toast.LENGTH_LONG).show()
            }.show()
    }
}