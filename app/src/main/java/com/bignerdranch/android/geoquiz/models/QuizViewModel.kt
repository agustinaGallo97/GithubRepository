package com.bignerdranch.android.geoquiz.models

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.geoquiz.controllers.QuestionController

private const val TAG = "QuiezViewModel"

class QuizViewModel : ViewModel() {
    var currentIndex = 0
    val questionBank = QuestionController.getInstance().questionBank

    val currentQuestion: Question
        get() = questionBank[currentIndex]

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].isAnswerTrue

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1) % questionBank.size
    }
}
