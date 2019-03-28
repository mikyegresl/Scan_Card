package com.example.gamezale.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEmployee(Employee employee);

    @Delete
    void deleteEmployee(Employee employee);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEmployee(Employee employee);

    @Query("SELECT * FROM current_user LIMIT 1")
    LiveData<Employee> getCurrentEmployeeLive();

    @Query("SELECT * FROM current_user LIMIT 1")
    Employee getCurrentEmployee();

    @Query("DELETE FROM current_user")
    void deleteAll();
}
