package com.example.myapplication.domain.repository

import com.example.myapplication.data.ToDo
import com.example.myapplication.data.ToDoDAO
import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImplement(private val dao: ToDoDAO): ToDoRepository
{
    override suspend fun insertToDo(toDoEntity: ToDo) {
        dao.insertToDo(toDoEntity)
    }

    override suspend fun deleteToDo(toDoEntity: ToDo) {
        dao.deleteToDo(toDoEntity)
    }

    override fun getAllToDo(): Flow<List<ToDo>> {
        return dao.getAllToDo()
    }

    override suspend fun getToDoById(id: Int): ToDo? {
        return dao.getToDoById(id)
    }

    override suspend fun getAllToDoByCategory(category: String): Flow<List<ToDo>>? {
        return dao.getAllToDoByCategory(category)
    }
}