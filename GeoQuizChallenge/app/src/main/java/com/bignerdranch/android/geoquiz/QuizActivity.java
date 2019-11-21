package com.bignerdranch.android.geoquiz;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {
  @SuppressWarnings("FieldCanBeLocal")
  private Button trueButton;
  @SuppressWarnings("FieldCanBeLocal")
  private Button falseButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    trueButton = findViewById(R.id.question_button_true);
    trueButton.setOnClickListener(v -> showToastCorrectly(true));

    falseButton = findViewById(R.id.question_button_false);
    falseButton.setOnClickListener(v -> showToastCorrectly(false));
  }

  private void showToastCorrectly(boolean isCorrect) {
    @StringRes
    int answerTest = isCorrect ? R.string.correct_toast : R.string.incorrect_toast;
    Toast toast = Toast.makeText(this, answerTest, Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
    toast.show();
  }
}

