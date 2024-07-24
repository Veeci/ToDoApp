package com.example.myapplication.presentation.main.fragment

import android.os.Bundle
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

    private  var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ToDoViewModel by activityViewModels{
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCategoryClick(category: String)
    {
        viewModel.setSelectedCategory(category)
    }

    private fun setFunctionalities(view: View)
    {
        binding.fabAddNote.setOnClickListener{
            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_addNoteFragment)
        }

        binding.categoryPersonal.setOnClickListener{
            onCategoryClick(binding.personalTV.text.toString())
            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_noteListFragment)
        }

        binding.categoryAcademic.setOnClickListener{
            onCategoryClick(binding.academicTV.text.toString())
            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_noteListFragment)
        }

        binding.categoryWork.setOnClickListener{
            onCategoryClick(binding.workTV.text.toString())
            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_noteListFragment)
        }

        binding.categoryOthers.setOnClickListener{
            onCategoryClick(binding.othersTV.text.toString())
            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_noteListFragment)
        }
    }
}