package com.bignerdranch.android.geoquiz.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.geoquiz.Controllers.QuestionController;
import com.bignerdranch.android.geoquiz.Models.Question;
import com.bignerdranch.android.geoquiz.R;

import java.util.HashSet;
import java.util.List;

import androidx.annotation.StringRes;

public class QuizActivity extends AppCompatActivity {
  private static final String TAG = "QuizActivity";
  private static final String KEY_INDEX = "index";

  @SuppressWarnings("FieldCanBeLocal")
  private Button trueButton;
  @SuppressWarnings("FieldCanBeLocal")
  private Button falseButton;
  private ImageButton nextButton;
  private ImageButton prevButton;
  private TextView questionTextView;
  private List<Question> questionBank = QuestionController.getInstance().getQuestionBank();
  private int currentIndex = 0;
  private int countOfCorrectQuestionsResolved = 0;
  private int countOfQuestionsResolved = 0;
  private HashSet<Question> resolvedQuestions = new HashSet();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate(Bundle) called");
    setContentView(R.layout.activity_quiz);

    if (savedInstanceState != null) {
      currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
    }

    questionTextView = findViewById(R.id.questionTextView);
    questionTextView.setOnClickListener(v -> {
      currentIndex = (currentIndex + 1) % questionBank.size();
      updateQuestion();
    });

    trueButton = findViewById(R.id.questionButtonTrue);
    trueButton.setOnClickListener(v -> showToastCorrectly(true));

    falseButton = findViewById(R.id.questionButtonFalse);
    falseButton.setOnClickListener(v -> showToastCorrectly(false));

    prevButton = findViewById(R.id.prevButton);
    prevButton.setOnClickListener(v -> {
      if (currentIndex > 0) {
        currentIndex = (currentIndex - 1) % questionBank.size();
        updateQuestion();
      }
    });
    nextButton = findViewById(R.id.nextButton);
    nextButton.setOnClickListener(v -> {
      currentIndex = (currentIndex + 1) % questionBank.size();
      updateQuestion();
    });

    updateQuestion();
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    Log.i(TAG, "onSaveInstanceState");
    savedInstanceState.putInt(KEY_INDEX, currentIndex);
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.d(TAG, "onStart() called");
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.d(TAG, "onPause() called");
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d(TAG, "onResume() called");
  }

  @Override
  public void onStop() {
    super.onStop();
    Log.d(TAG, "onStop() called");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy() called");
  }

  private void updateQuestion() {
    Question actualQuestion = questionBank.get(currentIndex);
    questionTextView.setText(actualQuestion.getTextResId());

    if (resolvedQuestions.contains(actualQuestion)) {
      disableButtons(false);
    } else {
      disableButtons(true);
    }

    if (countOfQuestionsResolved == questionBank.size()) {
      String percentage = (countOfCorrectQuestionsResolved) + "/" + (countOfQuestionsResolved);
      Toast.makeText(this, "El resultado promedio es: " + percentage, Toast.LENGTH_SHORT).show();
    }
  }

  private void showToastCorrectly(boolean isCorrect) {
    Question actualQuestion = questionBank.get(currentIndex);
    if (resolvedQuestions.contains(actualQuestion)) {
      disableButtons(false);
    } else {
      @StringRes
      int answerTest = isCorrect ? R.string.correct : R.string.incorrect;
      Toast toast = Toast.makeText(this, answerTest, Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
      toast.show();
      if (isCorrect) {
        countOfCorrectQuestionsResolved += 1;
      }
      countOfQuestionsResolved += 1;
      resolvedQuestions.add(actualQuestion);
      disableButtons(false);
    }
  }

  private void disableButtons(boolean value) {
    falseButton.setEnabled(value);
    trueButton.setEnabled(value);
  }
}

