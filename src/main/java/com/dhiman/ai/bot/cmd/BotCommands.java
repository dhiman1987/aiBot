package com.dhiman.ai.bot.cmd;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.ai.chat.ChatClient;
import org.springframework.shell.standard.commands.Completion;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ShellComponent
public class BotCommands {

    private final ChatClient chatClient;
    private final VectorStore vectors;

    public BotCommands(ChatClient chatClient, VectorStore vectors) {
        this.chatClient = chatClient;
        this.vectors = vectors;
    }

    @ShellMethod(key="ask", value = "Ask any question")
    public Map<String, String> ask(String message) {
        return Map.of("generation", chatClient.call(message));
    }

    @ShellMethod(key="askme", value = "Ask any question about myself")
    public String askme(String message) {
        String prompt = "Based on the following: {documents}";

        List<Document> documents = this.vectors.similaritySearch(message);
        String inlined = documents.stream()
                .map(Document::getContent)
                .collect(Collectors.joining(System.lineSeparator()));

        Message systemPrompt = new SystemPromptTemplate(prompt).createMessage(Map.of("documents", inlined));
        UserMessage userMessage = new UserMessage(message);

        String chatResponse = this.chatClient.call(
                        new Prompt(List.of(systemPrompt, userMessage)))
                .getResult()
                .getOutput()
                .getContent();
        return chatResponse;

        /*Completion completion = new Completion(
                this.chatClient.call(
                        new Prompt(List.of(systemPrompt, userMessage)))
                        .getResult()
                        .getOutput()
                        .getContent());
        return completion.toString();*/

    }
}
