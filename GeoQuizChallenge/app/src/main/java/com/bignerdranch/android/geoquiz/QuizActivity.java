package com.bignerdranch.android.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

public class QuizActivity extends AppCompatActivity {
  @SuppressWarnings("FieldCanBeLocal")
  private Button trueButton;
  @SuppressWarnings("FieldCanBeLocal")
  private Button falseButton;
  private ImageButton nextButton;
  private ImageButton prevButton;
  private TextView questionTextView;
  private Question[] questionBank = new Question[] {
      new Question(R.string.question_australia, true),
      new Question(R.string.question_oceans, true),
      new Question(R.string.question_mideast, true),
      new Question(R.string.question_africa, true),
      new Question(R.string.question_americas, true),
      new Question(R.string.question_asia, true),
  };
  private int currentIndex = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    questionTextView = findViewById(R.id.question_text_view);
    questionTextView.setOnClickListener(v -> {
      currentIndex = (currentIndex + 1) % questionBank.length;
      updateQuestion();
    });

    trueButton = findViewById(R.id.question_button_true);
    trueButton.setOnClickListener(v -> showToastCorrectly(true));

    falseButton = findViewById(R.id.question_button_false);
    falseButton.setOnClickListener(v -> showToastCorrectly(false));

    prevButton = findViewById(R.id.prev_button);
    prevButton.setOnClickListener(v -> {
      if (currentIndex > 0) {
        currentIndex = (currentIndex - 1) % questionBank.length;
        updateQuestion();
      }
    });

    nextButton = (ImageButton) findViewById(R.id.next_button);
    nextButton.setOnClickListener(v -> {
      currentIndex = (currentIndex + 1) % questionBank.length;
      updateQuestion();
    });

    updateQuestion();
  }

  private void updateQuestion() {
    int question = questionBank[currentIndex].getTextResId();
    questionTextView.setText(question);
  }

  private void showToastCorrectly(boolean isCorrect) {
    @StringRes
    int answerTest = isCorrect ? R.string.correct_toast : R.string.incorrect_toast;
    Toast toast = Toast.makeText(this, answerTest, Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
    toast.show();
  }
}

