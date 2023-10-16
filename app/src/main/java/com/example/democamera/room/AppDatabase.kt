package com.example.democamera.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.democamera.ui.RoomDataActivity

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase :RoomDatabase() {
    abstract fun PersonDao():PersonDao

}