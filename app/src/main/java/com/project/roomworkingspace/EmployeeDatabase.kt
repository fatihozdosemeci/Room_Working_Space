package com.project.roomworkingspace

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.jar.Attributes

@Database(entities = [EmployeeEntity::class, NameEntity::class], version = 1)
abstract class EmployeeDatabase: RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    companion object {

        @Volatile
        private var INSTANCE: EmployeeDatabase? = null

        fun getInstance(context: Context): EmployeeDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeeDatabase::class.java,
                        "employee_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }



}