package com.example.myapplication.domain.repository

import com.example.myapplication.data.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository
{
    suspend fun insertToDo(toDoEntity: ToDo)
    suspend fun deleteToDo(toDoEntity: ToDo)
    suspend fun getToDoById(id: Int): ToDo?
    fun getAllToDoByCategory(category: String): Flow<List<ToDo>>?
    fun getToDoCountByCategory(category: String): Flow<Int>
}