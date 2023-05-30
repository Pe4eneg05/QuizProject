package com.pechenegmobilecompanyltd.quizproject.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pechenegmobilecompanyltd.quizproject.R
import com.pechenegmobilecompanyltd.quizproject.data.DataClass
import com.pechenegmobilecompanyltd.quizproject.databinding.FragmentQuizBinding
import com.pechenegmobilecompanyltd.quizproject.entity.Question

var countTrueAnswer = 0

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countTrueAnswer = 0
        var balance = 0
        var countQuestion = 0
        var trueAnswerButton = 0

        val textDifficult = String.format(resources.getString(R.string.difficult, difficulty))
        binding.textDifficulty.text = textDifficult

        var textCountQuestion = String.format(
            resources.getString(
                R.string.count_question,
                (countQuestion + 1).toString()
            )
        )
        binding.textCountQuestion.text = textCountQuestion

        val questions = mutableListOf<Question>()
        val listQuestions = when (difficulty) {
            "Лёгкий" -> DataClass().listQuestionEasy.shuffled()
            "Средний" -> DataClass().listQuestionMedium.shuffled()
            "Тяжёлый" -> DataClass().listQuestionHard.shuffled()
            else -> DataClass().listQuestionEasy.shuffled()
        }
        for (i in 0..9) {
            questions.add(listQuestions[i])
        }

        bindingQuestion(questions, countQuestion)

        fun setTextCountQuestion() {
            textCountQuestion = String.format(
                resources.getString(
                    R.string.count_question,
                    (countQuestion + 1).toString()
                )
            )
            binding.textCountQuestion.text = textCountQuestion
        }

        fun saveAnswer() {
            countQuestion++
            binding.textCorrectAnswer.visibility = View.VISIBLE
            binding.textCorrectAnswer.text =
                String.format(resources.getString(R.string.correct_answer, questions[countQuestion - 1].correct_answer))
            binding.buttonNext.visibility = View.VISIBLE
            binding.firstAnswer.isEnabled = false
            binding.secondAnswer.isEnabled = false
            binding.thirdAnswer.isEnabled = false
        }

        binding.firstAnswer.setOnClickListener {
            saveAnswer()
            trueAnswerButton = R.id.first_answer
        }

        binding.secondAnswer.setOnClickListener {
            saveAnswer()
            trueAnswerButton = R.id.second_answer
        }

        binding.thirdAnswer.setOnClickListener {
            saveAnswer()
            trueAnswerButton = R.id.third_answer
        }

        binding.buttonNext.setOnClickListener {
            if (countQuestion == 9) {
                binding.buttonFinishQuiz.visibility = View.VISIBLE
                binding.buttonFinishQuiz.isEnabled = true
            }
            if (activity?.findViewById<TextView>(trueAnswerButton)?.text == questions[countQuestion - 1].correct_answer) {
                countTrueAnswer++
                bindingQuestion(questions, countQuestion)
                setTextCountQuestion()
            } else {
                bindingQuestion(questions, countQuestion)
                setTextCountQuestion()
            }
            binding.textCorrectAnswer.visibility = View.INVISIBLE
            binding.buttonNext.visibility = View.INVISIBLE
            binding.firstAnswer.isEnabled = true
            binding.secondAnswer.isEnabled = true
            binding.thirdAnswer.isEnabled = true
        }

        binding.buttonFinishQuiz.setOnClickListener {
            if (activity?.findViewById<TextView>(trueAnswerButton)?.text == questions[countQuestion - 1].correct_answer) {
                countTrueAnswer++
            }
            balance += countTrueAnswer
            savePointsPrefData(balance)
            findNavController().navigate(R.id.action_QuizFragment_to_ResultFragment)
        }
    }

    private fun bindingQuestion(questions: List<Question>, countQuestion: Int) {
        val listButtons =
            listOf(binding.firstAnswer, binding.secondAnswer, binding.thirdAnswer).shuffled()
        binding.textQuestion.text = decodeText(questions[countQuestion].question)
        listButtons[0].text = decodeText(questions[countQuestion].correct_answer)
        listButtons[1].text =
            decodeText(questions[countQuestion].second_answer)
        listButtons[2].text =
            decodeText(questions[countQuestion].third_answer)
    }

    private fun decodeText(text: String): String {
        return if (Build.VERSION.SDK_INT >= 24) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(text).toString()
        }
    }

    private fun getPointCount(): Int {
        sharedPreferences = context?.getSharedPreferences("point", Context.MODE_PRIVATE)
        return sharedPreferences!!.getInt("points", 0)
    }

    private fun savePointsPrefData(points: Int) {
        var pointsTotal = points
        when (difficulty) {
            "Лёгкий" -> pointsTotal *= 1
            "Средний" -> pointsTotal *= 2
            "Тяжёлый" -> pointsTotal *= 3
        }
        sharedPreferences = context?.getSharedPreferences("point", Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putInt("points", pointsTotal + getPointCount())
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}