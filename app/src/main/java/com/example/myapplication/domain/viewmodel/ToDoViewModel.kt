package com.example.myapplication.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ToDo
import com.example.myapplication.domain.repository.ToDoRepository
import kotlinx.coroutines.launch

class ToDoViewModel(private val repository: ToDoRepository): ViewModel()
{
    val allToDo: LiveData<List<ToDo>> = repository.getAllToDo().asLiveData()

    fun insert(toDo: ToDo) = viewModelScope.launch {
        repository.insertToDo(toDo)
    }

    fun delete(toDo: ToDo) = viewModelScope.launch {
        repository.deleteToDo(toDo)
    }

    fun getToDoById(id: Int) = viewModelScope.launch {
        repository.getToDoById(id)
    }
}

class ToDoViewModelFactory(private val repository: ToDoRepository): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ToDoViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return ToDoViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}