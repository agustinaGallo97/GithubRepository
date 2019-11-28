package com.bignerdranch.android.geoquiz.views

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.bignerdranch.android.geoquiz.R
import kotlinx.android.synthetic.main.activity_cheat.*

class CheatActivity : AppCompatActivity() {
    companion object {
        private val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"
        val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_is_shown"

        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }

        fun wasAnswerShown(result: Intent): Boolean {
            return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
        }
    }

    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        showAnswerButton.setOnClickListener { v ->
            answerTextView.setText(if (answerIsTrue) R.string.button_true else R.string.button_false)
            setAnswerShownResult(true)
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        setResult(Activity.RESULT_OK, data)
    }
}
