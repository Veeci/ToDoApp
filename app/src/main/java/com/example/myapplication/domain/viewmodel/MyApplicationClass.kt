package com.example.myapplication.domain.viewmodel

import android.app.Application
import com.example.myapplication.data.ToDoDatabase
import com.example.myapplication.domain.repository.ToDoRepository
import com.example.myapplication.domain.repository.ToDoRepositoryImplement

class MyApplicationClass : Application()
{
    val database by lazy { ToDoDatabase.getDatabase(this) }
    val repository by lazy { ToDoRepositoryImplement(database.dao) }
}