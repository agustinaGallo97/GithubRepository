package com.bignerdranch.android.geoquiz.controllers;
import com.bignerdranch.android.geoquiz.models.Question;
import com.bignerdranch.android.geoquiz.R;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;

public class QuestionController {
  @NonNull
  private static final QuestionController INSTANCE = new QuestionController();
  private List<Question> questionBank;

  private QuestionController() {
    questionBank = Arrays.asList(new Question(R.string.question_australia, true),
        new Question(R.string.question_oceans, true),
        new Question(R.string.question_mideast, true),
        new Question(R.string.question_africa, true),
        new Question(R.string.question_americas, true),
        new Question(R.string.question_asia, true));
  }

  @NonNull
  public static QuestionController getInstance() {
    return INSTANCE;
  }

  @NonNull
  public List<Question> getQuestionBank() {
    return questionBank;
  }
}
