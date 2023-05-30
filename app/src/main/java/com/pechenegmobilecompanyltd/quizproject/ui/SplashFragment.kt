package com.pechenegmobilecompanyltd.quizproject.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pechenegmobilecompanyltd.quizproject.R
import com.pechenegmobilecompanyltd.quizproject.databinding.FragmentSplashScreenBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed(
            { findNavController().navigate(R.id.action_SplashFragment_to_StartFragment) }, 5000
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}