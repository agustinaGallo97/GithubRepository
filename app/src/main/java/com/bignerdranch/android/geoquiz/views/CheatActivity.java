package com.bignerdranch.android.geoquiz.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.android.geoquiz.R;

public class CheatActivity extends AppCompatActivity {
  private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
  private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_is_shown";

  private boolean answerIsTrue;
  private TextView answerTextView;
  private Button showAnswerButton;

  @NonNull
  public static Intent newIntent(@NonNull Context packageContext, boolean answerIsTrue) {
    Intent intent = new Intent(packageContext, CheatActivity.class);
    intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
    return intent;
  }

  @NonNull
  public static boolean wasAnswerShown(Intent result) {
    return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);

    answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

    answerTextView = findViewById(R.id.answerTextView);

    showAnswerButton = findViewById(R.id.showAnswerButton);
    showAnswerButton.setOnClickListener(v -> {
      answerTextView.setText(answerIsTrue ? R.string.button_true : R.string.button_false);
      setAnswerShownResult(true);
    });
  }

  @NonNull
  private void setAnswerShownResult(boolean isAnswerShown) {
    Intent data = new Intent().putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
    setResult(RESULT_OK, data);
  }
}
