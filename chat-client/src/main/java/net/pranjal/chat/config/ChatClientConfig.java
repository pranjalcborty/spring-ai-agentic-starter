package net.pranjal.chat.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.cassandra.CassandraChatMemoryRepository;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ChatClientConfig {

    final CassandraChatMemoryRepository chatMemoryRepository;

    public ChatClientConfig(CassandraChatMemoryRepository chatMemoryRepository) {
        this.chatMemoryRepository = chatMemoryRepository;
    }

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel chatModel, VectorStore vectorStore, ToolCallbackProvider toolCallbackProvider) {
        ChatMemory chatMemory = MessageWindowChatMemory
                .builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(100)
                .build();

        Arrays.stream(toolCallbackProvider.getToolCallbacks())
                .forEach(c -> System.out.println(c.getToolDefinition()));

        return ChatClient
                .builder(chatModel)
                .defaultToolCallbacks(toolCallbackProvider)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore).build()
                ).build();
    }
}
