package pacman.plantmarket.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pacman.plantmarket.ai.promt.ChatPromt;
import pacman.plantmarket.service.ChatBotService;

@Service
@RequiredArgsConstructor
public class ChatBotServiceImpl implements ChatBotService {
    private ChatClient chatClient;

    @Autowired
    public void chatClient(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder
                .defaultSystem(ChatPromt.SYSTEM_PROMT)
                .build();
    }

    @Override
    public String generationChat(String userRequest) {
        return this.chatClient.prompt()
                .user(userRequest)
                .call()
                .content();
    }
}
