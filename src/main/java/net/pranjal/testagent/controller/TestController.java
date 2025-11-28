package net.pranjal.testagent.controller;

import net.pranjal.testagent.domain.ChampionsLeague;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@ResponseBody
public class TestController {

    private final ChatClient geminiChatClient;
    private final ChatClient ollamaChatClient;

    public TestController(@Qualifier("geminiChatClient") ChatClient geminiChatClient,
                          @Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {

        this.geminiChatClient = geminiChatClient;
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/geminiAssistant")
    ChampionsLeague inquireGemini(@RequestParam String question) {
        return geminiChatClient
                .prompt()
                .user(question)
                .call()
                .entity(ChampionsLeague.class);
    }

    @GetMapping("/ollamaAssistant")
    ChampionsLeague inquireOllama(@RequestParam String question) {
        return ollamaChatClient
                .prompt()
                .user(question)
                .call()
                .entity(ChampionsLeague.class);
    }

    @GetMapping("/assistant")
    Flux<String> assistant(@RequestParam String question) {
        Flux<String> geminiOutput = geminiChatClient
                .prompt()
                .user(question)
                .stream()
                .content();

        Flux<String> ollamaOutput = ollamaChatClient
                .prompt()
                .user(question)
                .stream()
                .content();

        return Flux.concat(geminiOutput, ollamaOutput);
    }
}
