package com.cjp.model.dto;

public class ExerciseRecommendationResponse {
    private String recommendation;

    public ExerciseRecommendationResponse(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
