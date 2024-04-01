package com.dhiman.ai.bot.cmd;

import com.dhiman.ai.bot.ChatService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ShellComponent
public class BotCommands {

    private final ChatService chatService;

    public BotCommands(ChatService chatService) {
        this.chatService = chatService;
    }

    @ShellMethod(key="ask", value = "Ask any question")
    public Map<String, String> ask(String message) {
        return chatService.chat(message);
    }

    @ShellMethod(key="askme", value = "Ask any question about myself")
    public String askme(String message) {
       return chatService.chatOnSpecificData(message);

    }
}
