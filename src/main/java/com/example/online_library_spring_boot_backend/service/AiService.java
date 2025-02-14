package com.example.online_library_spring_boot_backend.service;

import com.example.online_library_spring_boot_backend.dto.ChatCompletionRequest;
import com.example.online_library_spring_boot_backend.dto.ChatCompletionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class AiService {
    @Value("${openai.api.key}")
    private String openAiKey;

    @Value("${openai.api.url}")
    private String openAiUrl;

    private final WebClient webClient = WebClient.builder().build();

    public String getAiSummary(String prompt) {
        try {
            ChatCompletionRequest request = new ChatCompletionRequest();
            request.setModel("gpt-4o");
            request.setTemperature(0.7);

            ChatCompletionRequest.Message userMessage =
                    new ChatCompletionRequest.Message("user", prompt);

            request.setMessages(Collections.singletonList(userMessage));

            ChatCompletionResponse response = webClient.post()
                    .uri(openAiUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + openAiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ChatCompletionResponse.class)
                    .block(); // Synchronous

            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                return response.getChoices().get(0).getMessage().getContent();
            } else {
                return "No AI response available";
            }
        } catch (Exception e) {
            log.error("Error calling OpenAI API", e);
            return "Error fetching AI summary: " + e.getMessage();
        }
    }
}


