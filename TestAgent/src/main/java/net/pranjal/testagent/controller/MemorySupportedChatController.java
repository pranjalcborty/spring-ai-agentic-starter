package net.pranjal.testagent.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/memory")
public class MemorySupportedChatController {

    private final ChatClient ollamaClient;

    public MemorySupportedChatController(ChatClient ollamaChatClient) {
        this.ollamaClient = ollamaChatClient;
    }

    @GetMapping("/v1")
    public Flux<String> memorySupportedChatV1(@RequestParam String question) {
        return ollamaClient
                .prompt()
                .advisors(advisorSpec ->
                        advisorSpec.param(
                                ChatMemory.CONVERSATION_ID,
                                ChatMemory.DEFAULT_CONVERSATION_ID))
                .user(question)
                .stream()
                .content();
    }

    @GetMapping("/v2/{conversationId}")
    public Flux<String> memorySupportedChatV2(@RequestParam String question, @PathVariable String conversationId) {
        return ollamaClient
                .prompt()
                .advisors(advisorSpec ->
                        advisorSpec.param(
                                ChatMemory.CONVERSATION_ID,
                                conversationId))
                .user(question)
                .stream()
                .content();
    }
}
