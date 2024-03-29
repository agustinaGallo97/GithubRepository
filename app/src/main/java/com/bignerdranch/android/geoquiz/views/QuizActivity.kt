package com.bignerdranch.android.geoquiz.views

import androidx.appcompat.app.AppCompatActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast

import com.bignerdranch.android.geoquiz.BuildConfig
import com.bignerdranch.android.geoquiz.models.Question
import com.bignerdranch.android.geoquiz.R

import java.util.HashSet

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.geoquiz.models.QuizViewModel
import kotlinx.android.synthetic.main.activity_quiz.*

import timber.log.Timber

class QuizActivity : AppCompatActivity() {
    companion object {
        private val KEY_INDEX = "index"
        private val REQUEST_CODE_CHEAT = 0
    }

    private var countOfCorrectQuestionsResolved = 0
    private var countOfQuestionsResolved = 0
    private var isCheater: Boolean = false
    private val resolvedQuestions = HashSet<Question>()
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("onCreate(Bundle) called")
        setContentView(R.layout.activity_quiz)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        questionTextView.setOnClickListener { v ->
            quizViewModel.moveToNext()
            updateQuestion()
        }

        questionButtonTrue.setOnClickListener { v -> updateView(true) }

        questionButtonFalse.setOnClickListener { v -> updateView(false) }

        cheatButton.setOnClickListener { v ->
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

        prevButton.setOnClickListener { v ->
            if (quizViewModel.currentIndex > 0) {
                quizViewModel.moveToPrev()
                isCheater = false
                updateQuestion()
            }
        }
        nextButton.setOnClickListener { v ->
            quizViewModel.moveToNext()
            isCheater = false
            updateQuestion()
        }

        updateQuestion()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return
            }
            isCheater = CheatActivity.wasAnswerShown(data)
        }
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Timber.d("onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    public override fun onStart() {
        super.onStart()
        Timber.d("onStart() called")
    }

    public override fun onPause() {
        super.onPause()
        Timber.d("onPause() called")
    }

    public override fun onResume() {
        super.onResume()
        Timber.d("onResume() called")
    }

    public override fun onStop() {
        super.onStop()
        Timber.d("onStop() called")
    }

    public override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy() called")
    }

    private fun updateQuestion() {
        val actualQuestion = quizViewModel.currentQuestion
        questionTextView.setText(quizViewModel.currentQuestionText)
        setButtonsState(!resolvedQuestions.contains(actualQuestion))
        showPercentage()
    }

    private fun updateView(isCorrect: Boolean) {
        val actualQuestion = quizViewModel.currentQuestion
        if (resolvedQuestions.contains(actualQuestion)) {
            setButtonsState(false)
        } else {
            showToastCorrectly(isCorrect)
            increaseCounters(isCorrect)
            updateResolvedQuestions(actualQuestion)
        }
    }

    private fun showToastCorrectly(isCorrect: Boolean) {
        @StringRes
        val answerTest = if (isCheater) R.string.judgment_toast else getAnswerText(isCorrect)
        val toast = Toast.makeText(this, answerTest, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.show()
    }

    @StringRes
    private fun getAnswerText(isCorrect: Boolean): Int =
            if (isCorrect) R.string.correct else R.string.incorrect

    private fun increaseCounters(isCorrect: Boolean) {
        if (isCorrect) {
            countOfCorrectQuestionsResolved += 1
        }
        countOfQuestionsResolved += 1
    }

    private fun updateResolvedQuestions(actualQuestion: Question) {
        resolvedQuestions.add(actualQuestion)
        setButtonsState(false)
    }

    private fun setButtonsState(enabled: Boolean) {
        questionButtonFalse!!.isEnabled = enabled
        questionButtonTrue!!.isEnabled = enabled
    }

    private fun showPercentage() {
        if (countOfQuestionsResolved == quizViewModel.questionBank.size) {
            val percentage = "$countOfCorrectQuestionsResolved/$countOfQuestionsResolved"
            Toast.makeText(this, "The average is: $percentage", Toast.LENGTH_SHORT).show()
        }
    }

}
