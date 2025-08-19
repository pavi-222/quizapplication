package com.quizapp.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private Map<String, Integer> quizScores;

    public User(String name) {
        this.name = name;
        this.quizScores = new HashMap<>();
    }

    public void addScore(String quizName, int score) {
        quizScores.put(quizName, score);
    }

    public int getTotalScore() {
        return quizScores.values().stream().mapToInt(Integer::intValue).sum();
    }

    public String getName() { return name; }
    public Map<String, Integer> getQuizScores() { return quizScores; }
}