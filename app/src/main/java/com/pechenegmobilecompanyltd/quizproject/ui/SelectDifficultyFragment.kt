package com.pechenegmobilecompanyltd.quizproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pechenegmobilecompanyltd.quizproject.R
import com.pechenegmobilecompanyltd.quizproject.databinding.FragmentSelectDifficultyBinding

var difficulty = ""

class SelectDifficultyFragment : Fragment() {

    private var _binding: FragmentSelectDifficultyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectDifficultyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonEasy.setOnClickListener {
            difficulty = "Лёгкий"
            findNavController().navigate(R.id.action_SelectDifficultyFragment_to_QuizFragment)
        }

        binding.buttonMedium.setOnClickListener {
            difficulty = "Средний"
            findNavController().navigate(R.id.action_SelectDifficultyFragment_to_QuizFragment)
        }

        binding.buttonHard.setOnClickListener {
            difficulty = "Тяжёлый"
            findNavController().navigate(R.id.action_SelectDifficultyFragment_to_QuizFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}