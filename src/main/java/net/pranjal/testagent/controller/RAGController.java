package net.pranjal.testagent.controller;

import net.pranjal.testagent.rag.VisitedPlaces;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rag")
public class RAGController {

    public final ChatClient geminiChatClient;
    public final ChatClient ollamaChatClient;

    public RAGController(ChatClient geminiChatClient, ChatClient ollamaChatClient) {
        this.geminiChatClient = geminiChatClient;
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/visit")
    public VisitedPlaces models(
            @RequestParam(value = "message", defaultValue = "Give me all places I have visited.")
            String message) {

        return ollamaChatClient
                .prompt()
                .user(message)
                .call()
                .entity(VisitedPlaces.class);
    }
}
