package com.example.myapplication.presentation.main.fragment.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.data.ToDo
import com.example.myapplication.databinding.FragmentAddNoteBinding
import com.example.myapplication.domain.viewmodel.MyApplicationClass
import com.example.myapplication.domain.viewmodel.ToDoViewModel
import com.example.myapplication.domain.viewmodel.ToDoViewModelFactory

class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ToDoViewModel by activityViewModels{
        ToDoViewModelFactory((requireActivity().application as MyApplicationClass).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAdd.setOnClickListener{
            val title = binding.titleET
            val description = binding.descriptionET
            val priority = binding.priorityET
            val category = binding.categoryET

            if(title.text.isNotEmpty() && description.text.isNotEmpty())
            {
                val newToDo = ToDo(
                    title = title.text.toString(),
                    description = description.text.toString(),
                    priority = priority.text.toString().toInt(),
                    category = category.text.toString()
                )
                viewModel.insert(newToDo)
                Toast.makeText(context, "Congratulations! You have new thing to do!!!", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.action_addNoteFragment_to_mainFragment)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}