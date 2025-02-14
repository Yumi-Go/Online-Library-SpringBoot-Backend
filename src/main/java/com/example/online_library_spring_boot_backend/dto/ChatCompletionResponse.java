package com.example.online_library_spring_boot_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChatCompletionResponse {
    private List<Choice> choices;

    @Data
    @NoArgsConstructor
    public static class Choice {
        private int index;
        private Message message;
        private String finish_reason;

        @Data
        @NoArgsConstructor
        public static class Message {
            private String role;
            private String content;
        }
    }
}
