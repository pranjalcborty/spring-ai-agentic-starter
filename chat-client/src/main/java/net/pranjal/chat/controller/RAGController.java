package net.pranjal.chat.controller;

import net.pranjal.chat.domain.rag.VisitedPlaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    static final Logger log = LoggerFactory.getLogger(RAGController.class);

    private final VectorStore vectorStore;
    private final ChatClient ollamaChatClient;

    @Value("classpath:/data/visited.json")
    Resource visitedPlacesJson;

    public RAGController(ChatClient ollamaChatClient,
                         VectorStore vectorStore) {

        this.vectorStore = vectorStore;
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/visitedPlaces")
    public VisitedPlaces visitedPlaces(
            @RequestParam(
                    value = "message",
                    defaultValue = "Give me all places I have visited.")
            String message) {
        log.info("RAG Visited Places: {}", message);

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

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message") String message) {
        log.info("RAG Chat: {}", message);
        return ollamaChatClient
                .prompt()
                .advisors(advisorSpec ->
                        advisorSpec.param(
                                ChatMemory.CONVERSATION_ID,
                                ChatMemory.DEFAULT_CONVERSATION_ID))
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/pgload")
    public ResponseEntity<?, ?> modelsPGload() {
        log.info("RAG Pgload");
        TextReader reader = new TextReader(visitedPlacesJson);
        reader.getCustomMetadata().put("filename", "visited.json");

        List<Document> documentList = reader.get();
        vectorStore.add(documentList);

        return new ResponseEntity<>("Document loaded", HttpStatus.OK);
    }

    @GetMapping("/pgload/similarity")
    public List<Document> modelsPGloadRead() {
        log.info("RAG Pgload Similarity Search");
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query("Ottawa")
                        .topK(5)
                        .build());
    }
}
