package com.bignerdranch.android.geoquiz.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.geoquiz.BuildConfig;
import com.bignerdranch.android.geoquiz.controllers.QuestionController;
import com.bignerdranch.android.geoquiz.models.Question;
import com.bignerdranch.android.geoquiz.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.StringRes;
import timber.log.Timber;

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
  @NonNull
  private final Set<Question> resolvedQuestions = new HashSet();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
    Timber.d("onCreate(Bundle) called");
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
    trueButton.setOnClickListener(v -> updateView(true));

    falseButton = findViewById(R.id.questionButtonFalse);
    falseButton.setOnClickListener(v -> updateView(false));

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
    Timber.d("onSaveInstanceState");
    savedInstanceState.putInt(KEY_INDEX, currentIndex);
  }

  @Override
  public void onStart() {
    super.onStart();
    Timber.d("onStart() called");
  }

  @Override
  public void onPause() {
    super.onPause();
    Timber.d("onPause() called");
  }

  @Override
  public void onResume() {
    super.onResume();
    Timber.d("onResume() called");
  }

  @Override
  public void onStop() {
    super.onStop();
    Timber.d("onStop() called");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Timber.d("onDestroy() called");
  }

  private void updateQuestion() {
    Question actualQuestion = questionBank.get(currentIndex);
    questionTextView.setText(actualQuestion.getTextResId());
    setButtonsState(!(resolvedQuestions.contains(actualQuestion)));
    if (countOfQuestionsResolved == questionBank.size()) {
      String percentage = (countOfCorrectQuestionsResolved) + "/" + (countOfQuestionsResolved);
      Toast.makeText(this, "The average is: " + percentage, Toast.LENGTH_SHORT).show();
    }
  }

  private void updateView(boolean isCorrect) {
    Question actualQuestion = questionBank.get(currentIndex);
    if (resolvedQuestions.contains(actualQuestion)) {
      setButtonsState(false);
    } else {
      showToastCorrectly(isCorrect);
      increaseCounters(isCorrect);
      updateResolvedQuestions(actualQuestion);
    }
  }

  private void showToastCorrectly(boolean isCorrect) {
    @StringRes
    int answerTest = isCorrect ? R.string.correct : R.string.incorrect;
    Toast toast = Toast.makeText(this, answerTest, Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
    toast.show();
  }

  private void increaseCounters(boolean isCorrect) {
    if (isCorrect) {
      countOfCorrectQuestionsResolved += 1;
    }
    countOfQuestionsResolved += 1;
  }

  @NonNull
  private void updateResolvedQuestions(Question actualQuestion) {
    resolvedQuestions.add(actualQuestion);
    setButtonsState(false);
  }

  private void setButtonsState(boolean enabled) {
    falseButton.setEnabled(enabled);
    trueButton.setEnabled(enabled);
  }
}

