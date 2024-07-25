package com.example.myapplication.domain.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.ToDo

class ToDoAdapter(private val listener: OnNoteClickListener): RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>()
{
    private var todos: List<ToDo> = listOf()

    inner class ToDoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val title: TextView = itemView.findViewById(R.id.todoTitle)
        val description: TextView = itemView.findViewById(R.id.todoDescription)

        init {
            itemView.setOnClickListener{
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION)
                {
                    listener.onNoteClick(todos[position].id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return ToDoViewHolder(view)
    }

    override fun getItemCount() = todos.size

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val todo = todos[position]
        holder.title.text = todo.title
        holder.description.text = todo.description
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTodos(todos: List<ToDo>)
    {
        this.todos = todos
        notifyDataSetChanged()
    }
}