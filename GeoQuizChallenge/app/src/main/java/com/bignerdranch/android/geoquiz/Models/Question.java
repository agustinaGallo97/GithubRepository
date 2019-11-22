package com.bignerdranch.android.geoquiz.Models;

public class Question {
  private int textResId;
  private boolean answerTrue;

  public Question(int textResId, boolean answerTrue) {
    this.textResId = textResId;
    this.answerTrue = answerTrue;
  }

  public int getTextResId() {
    return this.textResId;
  }

  public void setTextResId(int textResId) {
    this.textResId = textResId;
  }

  public boolean isAnswerTrue() {
    return this.answerTrue;
  }

  public void setAnswerTrue(boolean answerTrue) {
    this.answerTrue = answerTrue;
  }
}
