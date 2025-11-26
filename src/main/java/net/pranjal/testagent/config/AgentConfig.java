package net.pranjal.testagent.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

//    @Bean
//    PromptChatMemoryAdvisor promptChatMemoryAdvisor(DataSource dataSource) {
//        var jdbc = JdbcChatMemoryRepository
//                .builder()
//                .dataSource(dataSource)
//                .build();
//
//        var chatMessageWindow = MessageWindowChatMemory
//                .builder()
//                .chatMemoryRepository(jdbc)
//                .build();
//
//        return PromptChatMemoryAdvisor
//                .builder(chatMessageWindow)
//                .build();
//    }
}
