package net.pranjal.testagent.controller;

import net.pranjal.testagent.domain.rag.VisitedPlaces;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rag")
public class RAGController {

    public final VectorStore vectorStore;
    public final ChatClient geminiChatClient;
    public final ChatClient ollamaChatClient;

    @Value("classpath:/data/visited.json")
    Resource visitedPlacesJson;

    public RAGController(ChatClient geminiChatClient,
                         ChatClient ollamaChatClient,
                         VectorStore vectorStore) {

        this.vectorStore = vectorStore;
        this.geminiChatClient = geminiChatClient;
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/visit")
    public VisitedPlaces models(
            @RequestParam(
                    value = "message",
                    defaultValue = "Give me all places I have visited.")
            String message) {

        return ollamaChatClient
                .prompt()
                .advisors(advisorSpec ->
                        advisorSpec.param(
                                ChatMemory.CONVERSATION_ID,
                                ChatMemory.DEFAULT_CONVERSATION_ID))
                .user(message)
                .call()
                .entity(VisitedPlaces.class);
    }

    @GetMapping("/pgvector")
    public VisitedPlaces modelsPG(
            @RequestParam(value = "message",
                    defaultValue = "Give me all places I have visited.")
            String message) {

        return ollamaChatClient
                .prompt()
                .advisors(advisorSpec ->
                        advisorSpec.param(
                                ChatMemory.CONVERSATION_ID,
                                ChatMemory.DEFAULT_CONVERSATION_ID))
                .user(message)
                .call()
                .entity(VisitedPlaces.class);
    }

    @GetMapping("/pgload")
    public ResponseEntity<?, ?> modelsPGload() {
        TextReader reader = new TextReader(visitedPlacesJson);
        reader.getCustomMetadata().put("filename", "visited.json");

        List<Document> documentList = reader.get();
        vectorStore.add(documentList);

        return new ResponseEntity<>("Document loaded", HttpStatus.OK);
    }

    @GetMapping("/pgload/read")
    public List<Document> modelsPGloadRead() {
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query("Ottawa")
                        .topK(5)
                        .build());
    }
}
