package com.example.myapplication.presentation.main.fragment.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNoteDetailBinding
import com.example.myapplication.domain.viewmodel.MyApplicationClass
import com.example.myapplication.domain.viewmodel.ToDoViewModel
import com.example.myapplication.domain.viewmodel.ToDoViewModelFactory

class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ToDoViewModel by activityViewModels{
        ToDoViewModelFactory((requireActivity().application as MyApplicationClass).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todoId = arguments?.getInt("todoID")?: return

        viewModel.loadToDoById(todoId)
        viewModel.noteDetails.observe(viewLifecycleOwner){note ->
            note?.let{
                binding.noteTitleTV.text = it.title
                binding.noteDescriptionTV.text = it.description
                binding.notePriorityTV.text = it.priority.toString()
                binding.noteCategoryTV.text = it.category
            }?: run {
                binding.categoryNameTV.text = getString(R.string.NoteDetail_notfound)
            }
        }

        binding.backIV.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_noteDetailFragment_to_noteListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}