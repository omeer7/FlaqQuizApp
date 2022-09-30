package com.omerbartu.flagquizapp.view



import android.graphics.Color
import android.icu.text.UnicodeSetSpanner.CountMethod
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.omerbartu.flagquizapp.database.DatabaseHelper
import com.omerbartu.flagquizapp.database.FlagDAO
import com.omerbartu.flagquizapp.databinding.FragmentQuizBinding
import com.omerbartu.flagquizapp.model.FlagModel
import java.util.concurrent.TimeUnit


class QuizFragment : Fragment() {

    private lateinit var databaseHelper:DatabaseHelper
    private lateinit var flagList:ArrayList<FlagModel>
    private lateinit var wrongList:ArrayList<FlagModel>
    private lateinit var  correctAnswer:FlagModel
    private lateinit var  allOption:HashSet<FlagModel>
    private val flagDAO=FlagDAO()

    private var correctCount=0
    private var wrongCount=0
    private var questionCount=0
    private lateinit var countDownTimer:CountDownTimer


    private var _binding: FragmentQuizBinding? = null

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(requireContext(),"Uygulamaya Hoşgeldiniz",Toast.LENGTH_LONG).show()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper= DatabaseHelper(requireContext())
        flagList=flagDAO.getFiveRandomFlag(databaseHelper)




         countDownTimer = object: CountDownTimer(11000,1000){
            override fun onTick(p0: Long) {

                binding.countDown.text= (p0/1000).toString() + " sec"
                if (p0/1000<= 3){

                    binding.countDown.setTextColor(Color.RED)
                }

            }

            override fun onFinish() {

                binding.optionA.isEnabled = false
                binding.optionB.isEnabled = false
                binding.optionC.isEnabled = false
                binding.optionD.isEnabled = false

                if (binding.optionA.text == correctAnswer.flag_name) {
                    binding.optionA.setBackgroundColor(Color.GRAY)
                } else if (binding.optionB.text == correctAnswer.flag_name) {
                    binding.optionB.setBackgroundColor(Color.GRAY)

                } else if (binding.optionC.text == correctAnswer.flag_name) {
                    binding.optionC.setBackgroundColor(Color.GRAY)
                } else {

                    binding.optionD.setBackgroundColor(Color.GRAY)
                }

                Handler(Looper.getMainLooper()).postDelayed({ changeQuestion(view) }, 750)

            }
        }
        loadQuestion()

        buttonClick(binding.optionA)
        buttonClick(binding.optionB)
        buttonClick(binding.optionC)
        buttonClick(binding.optionD)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun loadQuestion(){
        binding.countDown.setTextColor(Color.BLACK)
        countDownTimer.start()

        binding.correctText.setText("Correct: "+ correctCount.toString())
        binding.falseText.setText("Wrong: "+ wrongCount.toString())
        binding.questionText.text="QUESTION: " + (questionCount +1).toString()
        correctAnswer=flagList.get(questionCount)
        binding.imageView.setImageResource(resources.getIdentifier(correctAnswer.flag_image,"drawable","com.omerbartu.flagquizapp"))
        wrongList=flagDAO.getThreeFalseOption(databaseHelper,correctAnswer.flag_id)

        allOption= HashSet()
        allOption.add(correctAnswer)
        allOption.add(wrongList[0])
        allOption.add(wrongList[1])
        allOption.add(wrongList[2])

        binding.optionA.text= allOption.elementAt(0).flag_name
        binding.optionB.text=allOption.elementAt(1).flag_name
        binding.optionC.text=allOption.elementAt(2).flag_name
        binding.optionD.text=allOption.elementAt(3).flag_name

        binding.optionA.setBackgroundColor(Color.WHITE)
        binding.optionB.setBackgroundColor(Color.WHITE)
        binding.optionC.setBackgroundColor(Color.WHITE)
        binding.optionD.setBackgroundColor(Color.WHITE)

        binding.optionA.isEnabled=true
        binding.optionB.isEnabled=true
        binding.optionC.isEnabled=true
        binding.optionD.isEnabled=true


    }

    fun changeQuestion(view: View){
        questionCount++
        if (questionCount!=5){

            loadQuestion()
        }
        else{
            findNavController()
                .navigate(QuizFragmentDirections.
                actionQuizFragmentToResultFragment(correctCount,wrongCount))
        }
    }
    fun buttonClick(button:Button) {

            button.setOnClickListener {
                countDownTimer.cancel()

                binding.optionA.isEnabled = false
                binding.optionB.isEnabled = false
                binding.optionC.isEnabled = false
                binding.optionD.isEnabled = false

                if (correctAnswer.flag_name == button.text) {

                    button.setBackgroundColor(Color.GREEN)
                    correctCount++
                } else
                {
                    button.setBackgroundColor(Color.RED)
                    wrongCount++

                    if (binding.optionA.text == correctAnswer.flag_name) {
                        binding.optionA.setBackgroundColor(Color.GREEN)
                    } else if (binding.optionB.text == correctAnswer.flag_name) {
                        binding.optionB.setBackgroundColor(Color.GREEN)

                    } else if (binding.optionC.text == correctAnswer.flag_name) {
                        binding.optionC.setBackgroundColor(Color.GREEN)
                    } else {

                        binding.optionD.setBackgroundColor(Color.GREEN)
                    }
                }

                Handler(Looper.getMainLooper()).postDelayed({ changeQuestion(it) }, 500)


            }

    }
}
