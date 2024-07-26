package com.example.myapplication.presentation.main.fragment.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

        setFunction(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setFunction(view: View) {
        val spinnerPriority = binding.prioritySP
        ArrayAdapter.createFromResource(requireContext(), R.array.priority, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPriority.adapter = adapter
        }
        spinnerPriority.setSelection(0)

        val spinnerCategory = binding.categorySP
        ArrayAdapter.createFromResource(requireContext(), R.array.category, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter
        }
        spinnerCategory.setSelection(0)

        binding.buttonAdd.setOnClickListener{
            val title = binding.titleET
            val description = binding.descriptionET

            var priority = 0
            when(binding.prioritySP.selectedItemPosition)
            {
                1 -> priority = 1
                2 -> priority = 2
                3 -> priority = 3
                4 -> priority = 4
                5 -> priority = 5
            }

            var category = ""
            when(binding.categorySP.selectedItemPosition)
            {
                1 -> category = "Personal"
                2 -> category = "Academic"
                3 -> category = "Work"
                4 -> category = "Others"
            }

            if(title.text.isNotEmpty() && description.text.isNotEmpty())
            {
                val newToDo = ToDo(
                    title = title.text.toString(),
                    description = description.text.toString(),
                    priority = priority,
                    category = category
                )
                viewModel.insert(newToDo)
                Toast.makeText(context, "Congratulations! You have new thing to do!!!", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.action_addNoteFragment_to_mainFragment)
            }
            else
            {
                Toast.makeText(context, "C'mon!!! At least just fill in the title and describe something about it -.-'", Toast.LENGTH_SHORT).show()
            }
        }
    }
}