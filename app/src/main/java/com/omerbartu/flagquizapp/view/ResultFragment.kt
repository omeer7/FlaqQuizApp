package com.omerbartu.flagquizapp.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.omerbartu.flagquizapp.R
import com.omerbartu.flagquizapp.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private val args:ResultFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.resultText.text = args.correctCount.toString() +" Correct "+args.wrongCount.toString() +" False"
        binding.percentText.text = "% "+ ((args.correctCount*100)/5).toString() + " Success"

        binding.againButton.setOnClickListener {
            val action= ResultFragmentDirections.actionResultFragmentToQuizFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}
