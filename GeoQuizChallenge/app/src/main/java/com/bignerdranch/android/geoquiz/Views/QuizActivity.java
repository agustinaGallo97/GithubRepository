package com.bignerdranch.android.geoquiz.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.geoquiz.Controllers.QuestionController;
import com.bignerdranch.android.geoquiz.Models.Question;
import com.bignerdranch.android.geoquiz.R;

import java.util.List;

import androidx.annotation.StringRes;

public class QuizActivity extends AppCompatActivity {
  @SuppressWarnings("FieldCanBeLocal")
  private Button trueButton;
  @SuppressWarnings("FieldCanBeLocal")
  private Button falseButton;
  private ImageButton nextButton;
  private ImageButton prevButton;
  private TextView questionTextView;

  private List<Question> questionBank = QuestionController.getInstance().getQuestionBank();
  private int currentIndex = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

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

  private void updateQuestion() {
    int question = questionBank.get(currentIndex).getTextResId();
    questionTextView.setText(question);
  }

  private void showToastCorrectly(boolean isCorrect) {
    @StringRes
    int answerTest = isCorrect ? R.string.correct : R.string.incorrect;
    Toast toast = Toast.makeText(this, answerTest, Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
    toast.show();
  }
}

