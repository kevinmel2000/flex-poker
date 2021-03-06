package com.flexpoker.web.controller;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.flexpoker.framework.pushnotifier.PushNotificationPublisher;
import com.flexpoker.model.chat.incoming.ChatMessage;
import com.flexpoker.pushnotifications.ChatSentPushNotification;

@Controller
public class ChatController {

    private final PushNotificationPublisher pushNotificationPublisher;

    @Inject
    public ChatController(PushNotificationPublisher pushNotificationPublisher) {
        this.pushNotificationPublisher = pushNotificationPublisher;
    }

    @MessageMapping("/app/sendchatmessage")
    public void sendChatMessage(ChatMessage chatMessage, Principal principal) {
        pushNotificationPublisher.publish(new ChatSentPushNotification(chatMessage.getGameId(),
                chatMessage.getTableId(), chatMessage.getMessage(), principal.getName(), false));
    }

}
