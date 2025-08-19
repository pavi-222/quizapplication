package com.quizapp.model;

import java.util.List;

public class question {
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;
    private int points;
    private String category;

    public question(String questionText, List<String> options, 
                   int correctAnswerIndex, int points, String category) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.points = points;
        this.category = category;
    }

    // Getters
    public String getQuestionText() { return questionText; }
    public List<String> getOptions() { return options; }
    public int getCorrectAnswerIndex() { return correctAnswerIndex; }
    public int getPoints() { return points; }
    public String getCategory() { return category; }

    public boolean isCorrect(int answerIndex) {
        return answerIndex == correctAnswerIndex;
    }

    @Override
    public String toString() {
        return "Category: " + category + " | Points: " + points + 
               "\nQuestion: " + questionText;
    }
}
