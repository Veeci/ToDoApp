package com.example.myapplication.presentation.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
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

    private fun setFunction(view: View)
    {
        binding.buttonBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_onBoardingFragment)
        }

        binding.buttonSignUp.setOnClickListener {
            signUpWithFirebase(binding.emailETSignup.text.toString(), binding.passwordETSignup.text.toString())
        }
    }

    private fun signUpWithFirebase(email: String, password: String)
    {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful)
            {
                Toast.makeText(requireContext(), "Sign up successfully", Toast.LENGTH_LONG).show()
                Navigation.findNavController(requireView()).navigate(R.id.action_signupFragment_to_loginFragment)
            }
            else
            {
                Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
