package com.project.roomworkingspace

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName =  "employee-table")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String ="",
    @ColumnInfo(name = "email-id")
    val email:String = ""

    )
@Entity(tableName = "date-table")
data class NameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: ArrayList<String> = ArrayList()


)
