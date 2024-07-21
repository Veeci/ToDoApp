package com.example.myapplication.data.repository

import com.example.myapplication.data.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository
{
    suspend fun insertToDo(toDoEntity: ToDo)
    suspend fun deleteToDo(toDoEntity: ToDo)
    fun getAllToDo(): Flow<List<ToDo>>
    suspend fun getToDoById(id: Int): ToDo?
}