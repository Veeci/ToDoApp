package com.example.myapplication.domain.repository

import com.example.myapplication.data.ToDo
import com.example.myapplication.data.ToDoDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ToDoRepositoryImplement(private val dao: ToDoDAO): ToDoRepository
{
    override suspend fun insertToDo(toDoEntity: ToDo) {
        dao.insertToDo(toDoEntity)
    }

    override suspend fun updateToDo(toDoEntity: ToDo) {
        dao.updateToDo(toDoEntity)
    }

    override suspend fun deleteToDo(toDoEntity: ToDo) {
        dao.deleteToDo(toDoEntity)
    }

    override suspend fun getToDoById(id: Int): ToDo? {
        return dao.getToDoById(id)
    }

    override fun getAllToDoByCategory(category: String): Flow<List<ToDo>>? {
        return dao.getAllToDoByCategory(category)
    }

    override fun getToDoCountByCategory(category: String): Flow<Int> {
        return dao.getToDoCountByCategory(category).flowOn(Dispatchers.IO)
    }
}