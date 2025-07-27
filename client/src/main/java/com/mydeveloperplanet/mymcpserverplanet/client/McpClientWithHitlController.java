package com.mydeveloperplanet.mymcpserverplanet.client;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class McpClientWithHitlController {

    private final ChatModel chatModel;
    private final ToolCallingManager toolCallingManager;
    private final ChatOptions chatOptions;

    public McpClientWithHitlController(ChatModel chatModel, ToolCallbackProvider tools) {
        this.chatModel = chatModel;
        this.toolCallingManager = ToolCallingManager.builder().build();

        this.chatOptions = ToolCallingChatOptions.builder()
                .toolCallbacks(tools.getToolCallbacks())
                .internalToolExecutionEnabled(false)
                .build();
    }

    @GetMapping("/chathitl")
    String chat(@RequestParam String message) {

        Prompt prompt = new Prompt(message, chatOptions);

        ChatResponse chatResponse = chatModel.call(prompt);

        while (chatResponse.hasToolCalls()) {
            System.out.println("Chat response has tools calls");
            ToolExecutionResult toolExecutionResult = toolCallingManager.executeToolCalls(prompt, chatResponse);

            prompt = new Prompt(toolExecutionResult.conversationHistory(), chatOptions);

            chatResponse = chatModel.call(prompt);
        }

        return chatResponse.getResult().getOutput().getText();
    }

}
