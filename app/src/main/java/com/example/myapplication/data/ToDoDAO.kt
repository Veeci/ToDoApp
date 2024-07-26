package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDAO
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(toDoEntity: ToDo)

    @Update
    suspend fun updateToDo(toDoEntity: ToDo)

    @Delete
    suspend fun deleteToDo(toDoEntity: ToDo)

    @Query("SELECT * FROM todo_table WHERE id = :id")
    suspend fun getToDoById(id: Int): ToDo?

    @Query("SELECT * FROM todo_table WHERE category = :category")
    fun getAllToDoByCategory(category: String): Flow<List<ToDo>>?

    @Query("SELECT COUNT(*) FROM todo_table WHERE category = :category")
    fun getToDoCountByCategory(category: String): Flow<Int>

//    @Query("SELECT * FROM todo_table ORDER BY priority DESC")
//    fun sortToDoByPriority(): Flow<List<ToDo>>
}