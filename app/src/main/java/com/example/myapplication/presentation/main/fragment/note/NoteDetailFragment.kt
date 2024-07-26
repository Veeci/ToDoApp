package com.example.myapplication.presentation.main.fragment.note

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.data.ToDo
import com.example.myapplication.databinding.FragmentNoteDetailBinding
import com.example.myapplication.domain.viewmodel.MyApplicationClass
import com.example.myapplication.domain.viewmodel.ToDoViewModel
import com.example.myapplication.domain.viewmodel.ToDoViewModelFactory
import kotlinx.coroutines.launch

class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ToDoViewModel by activityViewModels{
        ToDoViewModelFactory((requireActivity().application as MyApplicationClass).repository)
    }

    private var todoId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoId = arguments?.getInt("todoID")?: 0

        showToDoInfo()

        setFunction(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToDoInfo()
    {
        viewModel.loadToDoById(todoId)
        viewModel.noteDetails.observe(viewLifecycleOwner){note ->
            note?.let{
                binding.noteTitleET.text = Editable.Factory.getInstance().newEditable(it.title)
                binding.noteDescriptionET.text = Editable.Factory.getInstance().newEditable(it.description)
                binding.notePriorityET.text = Editable.Factory.getInstance().newEditable(it.priority.toString())
                binding.noteCategoryET.text = Editable.Factory.getInstance().newEditable(it.category)
            }?: run {
                binding.categoryNameTV.text = getString(R.string.NoteDetail_notfound)
            }
        }
    }

    private fun setFunction(view: View)
    {
        binding.backIV.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_noteDetailFragment_to_noteListFragment)
        }

        binding.btnUpdate.setOnClickListener{
            updateToDo()
        }

        binding.btnDelete.setOnClickListener {
            deleteTodo()
        }
    }

    private fun updateToDo()
    {
        viewLifecycleOwner.lifecycleScope.launch {
            val deferredToDo = viewModel.getTodoById(todoId)
            val todo = deferredToDo.await()
            if(todo != null)
            {
                todo.title = binding.noteTitleET.text.toString()
                todo.description = binding.noteDescriptionET.text.toString()
                todo.category = binding.noteCategoryET.text.toString()
                todo.priority = binding.notePriorityET.text.toString().toInt()

                viewModel.update(todo)
                Toast.makeText(context, "Updated to do task successfully!", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(requireView()).navigate(R.id.action_noteDetailFragment_to_noteListFragment)
            }
            else
            {
                Toast.makeText(context, "Update failed! Something went wrong!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteTodo()
    {
        viewLifecycleOwner.lifecycleScope.launch {
            val deferredToDo = viewModel.getTodoById(todoId)
            val todo = deferredToDo.await()

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Delete Confirmation")
            builder.setMessage("Sure about that? Remember you cannot undo...")

            if(todo != null)
            {
                builder.setPositiveButton("Yes"){ dialog, _->
                    viewModel.delete(todo)
                    Toast.makeText(context, "Deleted to do task successfully!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    Navigation.findNavController(requireView()).navigate(R.id.action_noteDetailFragment_to_noteListFragment)
                }
                builder.setNegativeButton("Nahhh"){ dialog, _->
                    Toast.makeText(context, "Feel lucky to not press the Yes one?", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
            else
            {
                builder.setNegativeButton("No") { dialog, _ ->
                    Toast.makeText(context, "Delete failed! Something went wrong!!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
}