package net.pranjal.testagent.controller;

import net.pranjal.testagent.service.Tools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tools")
public class ToolCallingController {

    public final ChatClient ollamaChatClient;

    public ToolCallingController(ChatClient ollamaChatClient) {

        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message") String message) {
        return ollamaChatClient
                .prompt()
                .tools(new Tools())
                .advisors(advisorSpec ->
                        advisorSpec.param(
                                ChatMemory.CONVERSATION_ID,
                                ChatMemory.DEFAULT_CONVERSATION_ID))
                .user(message)
                .call()
                .content();
    }
}
