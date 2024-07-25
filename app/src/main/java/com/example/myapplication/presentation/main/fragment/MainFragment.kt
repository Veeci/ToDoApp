package com.example.myapplication.presentation.main.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.domain.viewmodel.MyApplicationClass
import com.example.myapplication.domain.viewmodel.ToDoViewModel
import com.example.myapplication.domain.viewmodel.ToDoViewModelFactory

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ToDoViewModel by activityViewModels {
        ToDoViewModelFactory((requireActivity().application as MyApplicationClass).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFunctionalities(view)
//        setUpObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCategoryClick(category: String) {
        viewModel.setSelectedCategory(category)
        viewModel.getAllToDoByCategory(category).observe(viewLifecycleOwner) { todos ->
            when(category){
                binding.personalTV.text.toString() -> binding.personalFilesCount.text = todos.size.toString()
                binding.academicTV.text.toString() -> binding.academicFilesCount.text = todos.size.toString()
                binding.workTV.text.toString() -> binding.workFilesCount.text = todos.size.toString()
                binding.othersTV.text.toString() -> binding.othersFilesCount.text = todos.size.toString()
            }
        }
    }

    private fun setFunctionalities(view: View) {
        binding.fabAddNote.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_addNoteFragment)
        }

        binding.categoryPersonalContainer.setOnClickListener {
            onCategoryClick(binding.personalTV.text.toString())
            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_noteListFragment)
        }

        binding.categoryAcademicContainer.setOnClickListener {
            onCategoryClick(binding.academicTV.text.toString())
            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_noteListFragment)
        }

        binding.categoryWorkContainer.setOnClickListener {
            onCategoryClick(binding.workTV.text.toString())
            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_noteListFragment)
        }

        binding.categoryOthersContainer.setOnClickListener {
            onCategoryClick(binding.othersTV.text.toString())
            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_noteListFragment)
        }

        viewModel.getToDoCountByCategory(binding.personalTV.text.toString())
        viewModel.getToDoCountByCategory(binding.academicTV.text.toString())
        viewModel.getToDoCountByCategory(binding.workTV.text.toString())
        viewModel.getToDoCountByCategory(binding.othersTV.text.toString())
    }

    private fun setUpObserver()
    {
        viewModel.countLiveData.observe(viewLifecycleOwner){count ->
            when(viewModel.selectedCategory.value){
                binding.personalTV.text.toString() -> binding.personalFilesCount.text = count.toString()
                binding.academicTV.text.toString() -> binding.academicFilesCount.text = count.toString()
                binding.workTV.text.toString() -> binding.workFilesCount.text = count.toString()
                binding.othersTV.text.toString() -> binding.othersFilesCount.text = count.toString()
            }
        }
    }
}