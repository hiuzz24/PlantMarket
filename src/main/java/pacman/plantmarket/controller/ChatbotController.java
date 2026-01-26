package pacman.plantmarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pacman.plantmarket.dto.ChatbotRequestDTO;
import pacman.plantmarket.dto.ChatbotResponseDTO;
import pacman.plantmarket.service.ChatBotService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot")
public class ChatbotController {
    private final ChatBotService chatBotService;

    @PostMapping("/chat")
    public ChatbotResponseDTO chatbot(@RequestBody ChatbotRequestDTO chatbotRequestDTO){
        return new ChatbotResponseDTO(chatBotService.generationChat(chatbotRequestDTO.getUserRequest()));
    }
}
