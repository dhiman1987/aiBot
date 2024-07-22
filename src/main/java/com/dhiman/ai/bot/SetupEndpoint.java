package com.dhiman.ai.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/setup")
public class SetupEndpoint {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final VectorStore vectorStore;
    @Value("${app.properties.text-source}")
    private String textFile;
    public SetupEndpoint(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostMapping
    public ResponseEntity<String> setUpVectorStore(){
        logger.debug("setting up vector store.");
        TextReader reader = new TextReader(this.textFile);
        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> documents = splitter.apply(reader.get());
        vectorStore.add(documents);
        logger.debug("setting up vector store completed.");
        return ResponseEntity.ok("Vector store setup successfully");
    }
}
