package com.bignerdranch.android.geoquiz;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.view.Gravity;

public class QuizActivity extends AppCompatActivity {
  private Button trueButton;
  private Button falseButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    trueButton = findViewById(R.id.question_button_true);
    trueButton.setOnClickListener(v -> {
      showToastCorrectly(true);
    });

    falseButton = findViewById(R.id.question_button_false);
    falseButton.setOnClickListener(v -> {
      showToastCorrectly(false);
    });
  }

  private void showToastCorrectly(boolean isCorrect) {
    Toast toast;
    if (isCorrect) {
      toast = Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT);
    } else {
      toast = Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT);
    }
    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
    toast.show();
  }
}

