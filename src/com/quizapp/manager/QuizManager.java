package com.quizapp.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.quizapp.model.question;
import com.quizapp.model.quiz;
import com.quizapp.model.User;

public class QuizManager {
    private List<quiz> availableQuizzes;
    private List<User> users;
    private Scanner scanner;

    public QuizManager() {
        this.availableQuizzes = new ArrayList<>();
        this.users = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Sample questions for different quizzes
        List<question> javaQuestions = new ArrayList<>();
        javaQuestions.add(new question("What is the default value of int in Java?",
                List.of("0", "null", "1", "undefined"), 0, 10, "Java Basics"));
        javaQuestions.add(new question("Which keyword is used for inheritance in Java?",
                List.of("extends", "implements", "inherits", "uses"), 0, 10, "Java OOP"));
        javaQuestions.add(new question("What is the size of int in Java?",
                List.of("32 bits", "16 bits", "64 bits", "Depends on platform"), 0, 15, "Java Basics"));

        List<question> mathQuestions = new ArrayList<>();
        mathQuestions.add(new question("What is 2 + 2 * 2?",
                List.of("6", "8", "4", "10"), 0, 5, "Arithmetic"));
        mathQuestions.add(new question("What is the value of π (pi) to two decimal places?",
                List.of("3.14", "3.15", "3.16", "3.13"), 0, 5, "Geometry"));

        availableQuizzes.add(new quiz(javaQuestions, "Java Programming Quiz"));
        availableQuizzes.add(new quiz(mathQuestions, "Mathematics Quiz"));
    }

    public void start() {
        System.out.println("Welcome to the Quiz Application!");
        
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    takeQuiz();
                    break;
                case 2:
                    viewLeaderboard();
                    break;
                case 3:
                    createNewQuiz();
                    break;
                case 4:
                    System.out.println("Thank you for using the Quiz Application!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Take a Quiz");
        System.out.println("2. View Leaderboard");
        System.out.println("3. Create New Quiz");
        System.out.println("4. Exit");
    }

    private void takeQuiz() {
        if (availableQuizzes.isEmpty()) {
            System.out.println("No quizzes available. Please create a quiz first.");
            return;
        }

        System.out.println("\nAvailable Quizzes:");
        for (int i = 0; i < availableQuizzes.size(); i++) {
            System.out.println((i + 1) + ". " + availableQuizzes.get(i).getQuizName());
        }
        
        int quizChoice = getIntInput("Select a quiz: ") - 1;
        if (quizChoice < 0 || quizChoice >= availableQuizzes.size()) {
            System.out.println("Invalid quiz selection.");
            return;
        }

        String userName = getStringInput("Enter your name: ");
        User user = new User(userName);
        users.add(user);

        quiz selectedQuiz = availableQuizzes.get(quizChoice);
        selectedQuiz.start();

        while (selectedQuiz.hasNextQuestion()) {
            question question = selectedQuiz.getNextQuestion();
            selectedQuiz.displayQuestion(question);
            
            int answer = getIntInput("", 1, question.getOptions().size());
            selectedQuiz.processAnswer(question, answer);
        }

        selectedQuiz.end();
        user.addScore(selectedQuiz.getQuizName(), selectedQuiz.getScore());
    }

    private void viewLeaderboard() {
        if (users.isEmpty()) {
            System.out.println("No quiz results available yet.");
            return;
        }

        System.out.println("\n=== LEADERBOARD ===");
        users.sort((u1, u2) -> Integer.compare(u2.getTotalScore(), u1.getTotalScore()));
        
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println((i + 1) + ". " + user.getName() + 
                             " - Total Score: " + user.getTotalScore());
            
            // Show individual quiz scores
            user.getQuizScores().forEach((quiz, score) -> {
                System.out.println("   " + quiz + ": " + score + " points");
            });
        }
    }

    private void createNewQuiz() {
        System.out.println("\n=== CREATE NEW QUIZ ===");
        String quizName = getStringInput("Enter quiz name: ");
        List<question> questions = new ArrayList<>();

        while (true) {
            System.out.println("\nAdd new question:");
            String questionText = getStringInput("Question text: ");
            
            List<String> options = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                options.add(getStringInput("Option " + (i + 1) + ": "));
            }
            
            int correctAnswer = getIntInput("Correct option (1-4): ", 1, 4);
            int points = getIntInput("Points for this question: ");
            String category = getStringInput("Category: ");

            questions.add(new question(questionText, options, correctAnswer - 1, points, category));
            
            String more = getStringInput("Add another question? (y/n): ");
            if (!more.equalsIgnoreCase("y")) {
                break;
            }
        }

        availableQuizzes.add(new quiz(questions, quizName));
        System.out.println("Quiz created successfully!");
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
            System.out.print(prompt);
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return input;
    }

    private int getIntInput(String prompt, int min, int max) {
        int input;
        do {
            input = getIntInput(prompt);
            if (input < min || input > max) {
                System.out.println("Please enter a number between " + min + " and " + max);
            }
        } while (input < min || input > max);
        return input;
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}