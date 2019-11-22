package com.bignerdranch.android.geoquiz.Controllers;

import com.bignerdranch.android.geoquiz.Models.Question;
import com.bignerdranch.android.geoquiz.R;
import java.util.ArrayList;
import java.util.List;

public class QuestionController {
  private static final QuestionController INSTANCE = new QuestionController();
  private List<Question> questionBank;

  private QuestionController() {
    questionBank = new ArrayList<Question>();
    questionBank.add(new Question(R.string.question_australia, true));
    questionBank.add(new Question(R.string.question_oceans, true));
    questionBank.add(new Question(R.string.question_mideast, true));
    questionBank.add(new Question(R.string.question_africa, true));
    questionBank.add(new Question(R.string.question_americas, true));
    questionBank.add(new Question(R.string.question_asia, true));
  }

  public static QuestionController getInstance() {
    return INSTANCE;
  }

  public List<Question> getQuestionBank() {
    return questionBank;
  }
}
