package com.cjp.controller;

import java.util.Arrays;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cjp.model.dto.ExerciseRecommendationRequest;
import com.cjp.model.dto.ExerciseRecommendationResponse;

@RestController
@RequestMapping("/api")
public class RecommendationController {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @PostMapping("/recommend-exercise")
    public ResponseEntity<?> recommendExercise(@RequestBody ExerciseRecommendationRequest request, @RequestHeader("Authorization") String token) {
        try {
            // 추가된 로그
            System.out.println("Received request: " + request);
            System.out.println("Authorization token: " + token);

            // GPT-3.5 API 호출
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "https://api.openai.com/v1/chat/completions";

            String prompt = generatePrompt(request);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + openaiApiKey);

            JSONObject body = new JSONObject();
            body.put("model", "gpt-3.5-turbo");
            body.put("messages", Arrays.asList(new JSONObject().put("role", "user").put("content", prompt)));

            HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

            String recommendation = extractRecommendation(response.getBody());
            System.out.println(recommendation);
            return ResponseEntity.ok(new ExerciseRecommendationResponse(recommendation));
        } catch (Exception e) {
            e.printStackTrace(); // 콘솔에 예외 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("운동 추천에 실패하였습니다.");
        }
    }

    private String generatePrompt(ExerciseRecommendationRequest request) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("여기 몇 가지 운동 관련 질문들과 대답이 있습니다. :\n");
        for (int i = 0; i < request.getQuestions().size(); i++) {
            prompt.append(request.getQuestions().get(i)).append(" ").append(request.getAnswers().get(i)).append("\n");
        }
        prompt.append("대답들에 근거하여, 이 사람에게 적절한 운동을 추천해 주세요. 상담사 처럼요. 운동 이름과 운동 방법을 자세히 알려주면 좋겠네요. 가능하면 시간당 예상 소모 칼로리 양도 알려 주시고, 보기 좋게 한 줄에 한 문장씩 출력해주세요.");
        return prompt.toString();
    }

    private String extractRecommendation(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
    }
}
