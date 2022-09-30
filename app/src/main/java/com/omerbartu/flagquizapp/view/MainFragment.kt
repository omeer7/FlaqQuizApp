package com.omerbartu.flagquizapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.info.sqlitekullanimihazirveritabani.DatabaseCopyHelper
import com.omerbartu.flagquizapp.database.DatabaseHelper
import com.omerbartu.flagquizapp.database.FlagDAO
import com.omerbartu.flagquizapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        copyDatabase()

        binding.startButton.setOnClickListener {
            val action= MainFragmentDirections.actionMainFragmentToQuizFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun copyDatabase(){
        try {
            val databaseHelper=DatabaseCopyHelper(requireContext())
            databaseHelper.createDataBase()
            databaseHelper.openDataBase()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

}