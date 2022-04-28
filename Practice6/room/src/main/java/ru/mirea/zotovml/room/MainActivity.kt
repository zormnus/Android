package ru.mirea.zotovml.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private var TAG: String? = MainActivity::class.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db:AppDatabase = App.instance.getDatabase()
        val employeeDao:EmployeeDao = db.EmployeeDao()

        var employee = Employee()
        employee.id = 1
        employee.name = "John Smith"
        employee.salary = 10000

        employeeDao.insert(employee)
        var employees:List<Employee> = employeeDao.getAll()
        employee = employeeDao.getById(1)
        employee.salary = 20000
        employeeDao.update(employee)
        Log.d(TAG, employee.name + " " + employee.salary)

    }
}