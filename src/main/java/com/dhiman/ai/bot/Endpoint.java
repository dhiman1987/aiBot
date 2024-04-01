package com.dhiman.ai.bot;

import com.dhiman.ai.bot.records.ChatInput;
import com.dhiman.ai.bot.records.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/chat")
public class Endpoint {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ChatService chatService;

    public Endpoint(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<?> chat(@RequestBody ChatInput chatInput){
        logger.debug("ChatInput received {}",chatInput.toString());
        if(null == chatInput.messageText()){
            return ResponseEntity.badRequest().body("empty chat");
        }
        return ResponseEntity.ok().body(new Message(UUID.randomUUID().toString(),
                false,
                chatService.chatOnSpecificData(chatInput.messageText()),
                System.currentTimeMillis()));
    }
}
