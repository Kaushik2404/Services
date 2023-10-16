package com.example.democamera.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDao {

    @Query("Select * from Person ")
        suspend fun getAll():List<Person>

        @Insert
        suspend fun insertPerson(person: List<Person>)

        @Delete
        suspend fun deletePerson(person: Person)
}