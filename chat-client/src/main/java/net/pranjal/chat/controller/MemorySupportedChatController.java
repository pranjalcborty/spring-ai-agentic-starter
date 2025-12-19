package net.pranjal.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/memory")
public class MemorySupportedChatController {

    static final Logger log = LoggerFactory.getLogger(MemorySupportedChatController.class);

    private final ChatClient ollamaClient;

    public MemorySupportedChatController(ChatClient ollamaChatClient) {
        this.ollamaClient = ollamaChatClient;
    }

    @GetMapping("/v1")
    public Flux<String> memorySupportedChatV1(@RequestParam String question) {
        log.info("Memory supported chat v1 question: {}", question);
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
        log.info("Memory supported chat v2 question: {}", question);
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
