package com.example.myapplication.presentation.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.presentation.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFunction(view)

    }

    private fun setFunction(view: View)
    {
        binding.buttonBack.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_onBoardingFragment)
        }

        binding.buttonLogin.setOnClickListener {
            signInWithFirebase(binding.emailETLogin.text.toString(), binding.passwordETLogin.text.toString())
        }

        binding.forgotPasswordButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }
    }

    private fun signInWithFirebase(email: String, password: String)
    {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful)
            {
                Toast.makeText(requireContext(), "Sign in successfully", Toast.LENGTH_LONG).show()
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
            else
            {
                Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}