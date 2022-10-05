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

    @Query("SELECT * FROM `employee-table` where name=:name")
    fun fetchEmployeeByName(name: String):Flow<EmployeeEntity>

    @Insert
    suspend fun insert(employeeEntity: WorkEntity)

    @Update
    suspend fun update(employeeEntity: WorkEntity)

    @Delete
    suspend fun delete(employeeEntity: WorkEntity)

    @Query("SELECT * FROM `work-table`")
    fun fetchAllDates():Flow<List<WorkEntity>>

    @Query("SELECT * FROM `work-table` where id=:id")
    fun fetchDateById(id: Int):Flow<WorkEntity>

    @Insert
    suspend fun insert(employeeEntity: EmployeeWorkCrossRef)

    @Update
    suspend fun update(employeeEntity: EmployeeWorkCrossRef)

    @Delete
    suspend fun delete(employeeEntity: EmployeeWorkCrossRef)

    @Query("SELECT * FROM `employee-work-table` ")
    fun fetchAllWorks():Flow<List<EmployeeWorkCrossRef>>

    @Query("SELECT * FROM `employee-work-table` where employeeId=:id")
    fun fetchWorksById(id: Int):Flow<EmployeeWorkCrossRef>

}
