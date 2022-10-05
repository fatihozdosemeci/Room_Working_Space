package com.project.roomworkingspace

import androidx.room.*

@Entity(tableName =  "employee-table")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String ="",
    @ColumnInfo(name = "email-id")
    val email:String = ""

    )
@Entity(tableName = "work-table")
data class WorkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val workName: String ="",
    @ColumnInfo(name = "workTime")
    val workTime : Int = 0
)

@Entity(tableName = "employee-work-table")
data class EmployeeWorkCrossRef(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "employeeId")
    val employeeId : Int,
    val workId : Int
)

data class EmployeeWithWork(
    @Embedded val employee: EmployeeEntity,
    @Relation(
        parentColumn = "employeeId",
        entityColumn = "workId",
        associateBy = Junction(EmployeeWorkCrossRef::class)
    )
    val works: List<WorkEntity>
)

data class WorkWithEmployee(
    @Embedded val work: WorkEntity,
    @Relation(
        parentColumn = "workId",
        entityColumn = "employeeId",
        associateBy = Junction(EmployeeWorkCrossRef::class)
    )
    val works: List<EmployeeEntity>
)
