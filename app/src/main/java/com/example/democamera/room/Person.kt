package com.example.democamera.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id :Int,

    @ColumnInfo
    val name:String,

    @ColumnInfo
    val age:Int,

)
