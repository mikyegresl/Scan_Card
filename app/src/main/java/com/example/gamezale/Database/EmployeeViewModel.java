package com.example.gamezale.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.gamezale.Database.Database;
import com.example.gamezale.Database.Employee;
import com.example.gamezale.Database.EmployeeDao;

public class EmployeeViewModel extends AndroidViewModel
{
    private EmployeeDao mEmployeeDao;
    private LiveData<Employee> currentEmployee;

    public EmployeeViewModel(@NonNull Application application)
    {
        super(application);
        mEmployeeDao = Database.getDatabase(application).employeeDao();
        currentEmployee = mEmployeeDao.getCurrentEmployeeLive();
    }

    public void insert(Employee employee)
    {
        mEmployeeDao.insertEmployee(employee);
    }

    public void update(Employee employee)
    {
        mEmployeeDao.updateEmployee(employee);
    }

    public Employee getCurrentEmployee()
    {
        return mEmployeeDao.getCurrentEmployee();
    }

    public LiveData<Employee> getCurrentEmployeeLive()
    {
        return mEmployeeDao.getCurrentEmployeeLive();
    }

    public void clearData()
    {
        mEmployeeDao.deleteAll();
    }

}
