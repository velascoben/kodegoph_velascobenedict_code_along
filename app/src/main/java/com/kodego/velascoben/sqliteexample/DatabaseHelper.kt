package com.kodego.velascoben.sqliteexample

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper (context, "company.db",null,1){
    // This is called to create a new Database
    override fun onCreate(db: SQLiteDatabase?) {
        var createTable : String = "CREATE TABLE EMPLOYEE_TABLE (id integer primary key autoincrement, name varchar(30), salary int)"
        db?.execSQL(createTable)
    }

    // Whenever there are changes to the Database e.g. Changes in Column
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // No function
    }

    fun addOne (employeeModel: EmployeeModel) {
        var db = this.writableDatabase
        var cv = ContentValues()

        cv.put("name",employeeModel.name)
        cv.put("salary",employeeModel.salary)

        db.insert("EMPLOYEE_TABLE",null,cv)
    }

    fun getAllData() : MutableList<EmployeeModel> {
        var returnList = ArrayList<EmployeeModel>()
        var queryString = "SELECT * FROM EMPLOYEE_TABLE"
        var db = this.readableDatabase

        var cursor : Cursor = db.rawQuery(queryString,null)

        if(cursor.moveToFirst()) {
            do{
                var id = cursor.getInt(0)
                var name = cursor.getString(1)
                var salary = cursor.getInt(2)

                var newEmployeeModel : EmployeeModel = EmployeeModel(id,name,salary)
                returnList.add(newEmployeeModel)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return returnList
    }

    fun deleteOne(employeeModel: EmployeeModel) {
        var db = this.writableDatabase
        var queryString = "DELETE FROM EMPLOYEE_TABLE WHERE id = ${employeeModel.id}"
        var cursor = db.rawQuery(queryString,null)
        cursor.moveToFirst()
    }
}

