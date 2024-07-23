package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDAO
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(toDoEntity: ToDo)

    @Delete
    suspend fun deleteToDo(toDoEntity: ToDo)

    @Query("SELECT * FROM todo_table")
    fun getAllToDo(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo_table WHERE id = :id")
    suspend fun getToDoById(id: Int): ToDo?

    @Query("SELECT * FROM todo_table WHERE category = :category")
    suspend fun getAllToDoByCategory(category: String): Flow<List<ToDo>>?
}