package com.quizapp.model;

import java.util.List;

public class quiz {
    private List<question> questions;
    private int currentQuestionIndex;
    private int score;
    private String quizName;
    private long startTime;
    private long endTime;

    public quiz(List<question> questions, String quizName) {
        this.questions = questions;
        this.quizName = quizName;
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        System.out.println("Starting Quiz: " + quizName);
        System.out.println("Total Questions: " + questions.size());
        System.out.println("=".repeat(50));
    }

    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size();
    }

    public question getNextQuestion() {
        if (hasNextQuestion()) {
            return questions.get(currentQuestionIndex++);
        }
        return null;
    }

    public void displayQuestion(question question) {
        System.out.println("\n" + question.toString());
        List<String> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.print("Your answer (1-" + options.size() + "): ");
    }

    public void processAnswer(question question, int answerIndex) {
        if (question.isCorrect(answerIndex - 1)) {
            score += question.getPoints();
            System.out.println("✓ Correct! +" + question.getPoints() + " points");
        } else {
            System.out.println("✗ Incorrect! The correct answer was: " + 
                (question.getCorrectAnswerIndex() + 1) + ". " + 
                question.getOptions().get(question.getCorrectAnswerIndex()));
        }
    }

    public void end() {
        endTime = System.currentTimeMillis();
        long timeTaken = (endTime - startTime) / 1000;
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("QUIZ COMPLETED!");
        System.out.println("=".repeat(50));
        System.out.println("Quiz: " + quizName);
        System.out.println("Total Score: " + score + "/" + getTotalPossiblePoints());
        System.out.println("Time Taken: " + timeTaken + " seconds");
        System.out.println("Accuracy: " + getAccuracy() + "%");
    }

    private int getTotalPossiblePoints() {
        return questions.stream().mapToInt(question::getPoints).sum();
    }

    private double getAccuracy() {
        int answered = currentQuestionIndex;
        if (answered == 0) return 0;
        return ((double) score / getTotalPossiblePoints()) * 100;
    }

    public int getScore() { return score; }
    public String getQuizName() { return quizName; }
}
