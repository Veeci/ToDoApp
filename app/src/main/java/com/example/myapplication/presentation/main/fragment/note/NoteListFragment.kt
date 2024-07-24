package com.example.myapplication.presentation.main.fragment.note

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNoteListBinding
import com.example.myapplication.domain.adapter.ToDoAdapter
import com.example.myapplication.domain.viewmodel.MyApplicationClass
import com.example.myapplication.domain.viewmodel.ToDoViewModel
import com.example.myapplication.domain.viewmodel.ToDoViewModelFactory

class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ToDoViewModel by activityViewModels{
        ToDoViewModelFactory((requireActivity().application as MyApplicationClass).repository)
    }
    private lateinit var adapter: ToDoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ToDoAdapter()

        binding.listRV.layoutManager = LinearLayoutManager(requireContext())
        binding.listRV.adapter = adapter

        binding.categoryNameTV.text = viewModel.selectedCategory.value.toString()

        binding.backIV.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_noteListFragment_to_mainFragment)
        }

        viewModel.selectedCategory.observe(viewLifecycleOwner){ category ->
            category?.let {
                viewModel.getAllToDoByCategory(category).observe(viewLifecycleOwner) { todos ->
                    Log.d("NoteListFragment", "Received todos: $it")
                    todos?.let { adapter.setTodos(it) }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}