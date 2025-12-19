package net.pranjal.chat.controller;

import net.pranjal.chat.domain.ChampionsLeague;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/assistant")
public class TestController {

    static final Logger log = LoggerFactory.getLogger(TestController.class);

    private final ChatClient ollamaChatClient;

    public TestController(@Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {

        this.ollamaChatClient = ollamaChatClient;
    }

//    @GetMapping("/gemini")
//    ChampionsLeague inquireGemini(@RequestParam String question) {
//        return geminiChatClient
//                .prompt()
//                .user(question)
//                .call()
//                .entity(ChampionsLeague.class);
//    }

    @GetMapping("/ollama")
    ChampionsLeague inquireOllama(@RequestParam String question) {
        log.info("Ollama structured output question: {}", question);
        return ollamaChatClient
                .prompt()
                .user(question)
                .call()
                .entity(ChampionsLeague.class);
    }

    @GetMapping("/combined")
    Flux<String> assistant(@RequestParam String question) {
        log.info("Combined structured output question: {}", question);
//        Flux<String> geminiOutput = geminiChatClient
//                .prompt()
//                .user(question)
//                .stream()
//                .content();

        Flux<String> ollamaOutput = ollamaChatClient
                .prompt()
                .user(question)
                .stream()
                .content();

//        return Flux.concat(geminiOutput, ollamaOutput);
        return ollamaOutput;
    }
}
