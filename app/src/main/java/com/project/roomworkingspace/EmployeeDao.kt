package com.project.roomworkingspace

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Insert
    suspend fun insert(employeeEntity: EmployeeEntity)

    @Update
    suspend fun update(employeeEntity: EmployeeEntity)

    @Delete
    suspend fun delete(employeeEntity: EmployeeEntity)

    @Query("SELECT * FROM `employee-table`")
    fun fetchAllEmployees():Flow<List<EmployeeEntity>>

    @Query("SELECT * FROM `employee-table` where id=:id")
    fun fetchEmployeeById(id: Int):Flow<EmployeeEntity>

    @Insert
    suspend fun insert(employeeEntity: NameEntity)

    @Update
    suspend fun update(employeeEntity: NameEntity)

    @Delete
    suspend fun delete(employeeEntity: NameEntity)

    @Query("SELECT * FROM `date-table`")
    fun fetchAllDates():Flow<List<NameEntity>>

    @Query("SELECT * FROM `date-table` where id=:id")
    fun fetchDateById(id: Int):Flow<NameEntity>

}
