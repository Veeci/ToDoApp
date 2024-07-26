package com.example.myapplication.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ToDo
import com.example.myapplication.domain.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ToDoViewModel(private val repository: ToDoRepository) : ViewModel() {

    fun insert(toDo: ToDo) = viewModelScope.launch {
        repository.insertToDo(toDo)
    }

    fun update(toDo: ToDo) = viewModelScope.launch {
        repository.updateToDo(toDo)
    }

    fun delete(toDo: ToDo) = viewModelScope.launch {
        repository.deleteToDo(toDo)
    }

    fun getTodoById(id: Int) = viewModelScope.async {
        repository.getToDoById(id)
    }

    fun getAllToDoByCategory(category: String): LiveData<List<ToDo>> {
        return repository.getAllToDoByCategory(category)?.asLiveData()!!
    }

    //To identify which category is being selected
    val selectedCategory = MutableLiveData<String>()
    fun setSelectedCategory(category: String) {
        selectedCategory.value = category
    }

    //To display detail of the note
    private val _noteDetails = MutableLiveData<ToDo?>()
    val noteDetails: LiveData<ToDo?> get() = _noteDetails

    fun loadToDoById(id: Int) = viewModelScope.launch {
        val todo = repository.getToDoById(id)
        todo?.let {
            _noteDetails.postValue(it)
        } ?: run {
            _noteDetails.postValue(null)
        }
    }

    //To count number of notes belong to that category
    private val _countLiveData = MutableLiveData<Int>()
    val countLiveData: LiveData<Int> get() = _countLiveData

    fun getToDoCountByCategory(category: String) {
        viewModelScope.launch {
            repository.getToDoCountByCategory(category)
                .flowOn(Dispatchers.IO)
                .collect { count ->
                    _countLiveData.postValue(count)
                }
        }
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