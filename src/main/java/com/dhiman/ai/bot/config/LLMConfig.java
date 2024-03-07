package com.dhiman.ai.bot.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Configuration
public class LLMConfig {
    @Value("${app.properties.setup-vector-store}")
    private boolean setupVectorStore;
    @Value("${app.properties.text-source}")
    private String textFile;

    @Bean
    VectorStore vectors(JdbcTemplate jdbcTemplate, EmbeddingClient embedding){
        PgVectorStore pgVectorStore = new PgVectorStore(jdbcTemplate, embedding);
        if(setupVectorStore){
            TextReader reader = new TextReader(this.textFile);
            TokenTextSplitter splitter = new TokenTextSplitter();
            List<Document> documents = splitter.apply(reader.get());
            pgVectorStore.add(documents);
        }
        return pgVectorStore;
    }
}
