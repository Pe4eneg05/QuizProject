package com.pechenegmobilecompanyltd.quizproject.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pechenegmobilecompanyltd.quizproject.R
import com.pechenegmobilecompanyltd.quizproject.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textResult = if (countTrueAnswer > 5) "Хорошая работа!" else "Попробуй снова!"

        binding.resultText.text = "Твой результат:\n\n$countTrueAnswer/10\n\n$textResult"

        binding.buttonFinish.setOnClickListener {
            findNavController().navigate(R.id.action_ResultFragment_to_StartFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}