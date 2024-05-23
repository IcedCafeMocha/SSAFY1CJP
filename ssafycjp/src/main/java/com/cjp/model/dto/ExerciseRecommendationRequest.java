package com.cjp.model.dto;

import java.util.List;

public class ExerciseRecommendationRequest {
    private List<String> questions;
    private List<String> answers;

    // Getters and setters
    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
