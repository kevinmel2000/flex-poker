package com.flexpoker.pushnotificationhandlers;

import javax.inject.Inject;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.flexpoker.framework.pushnotifier.PushNotificationHandler;
import com.flexpoker.login.repository.LoginRepository;
import com.flexpoker.pushnotifications.SendUserPocketCardsPushNotification;
import com.flexpoker.util.MessagingConstants;
import com.flexpoker.web.dto.outgoing.PocketCardsDTO;

@Component
public class SendUserPocketCardsPushNotificationHandler
        implements PushNotificationHandler<SendUserPocketCardsPushNotification> {

    private final LoginRepository loginRepository;

    private final SimpMessageSendingOperations messagingTemplate;

    @Inject
    public SendUserPocketCardsPushNotificationHandler(LoginRepository loginRepository,
            SimpMessageSendingOperations messagingTemplate) {
        this.loginRepository = loginRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Async
    @Override
    public void handle(SendUserPocketCardsPushNotification pushNotification) {
        var username = loginRepository.fetchUsernameByAggregateId(pushNotification.getPlayerId());
        var pocketCardsDTO = new PocketCardsDTO(pushNotification.getHandId(),
                pushNotification.getPocketCards().getCard1().getId(),
                pushNotification.getPocketCards().getCard2().getId());
        messagingTemplate.convertAndSendToUser(username, MessagingConstants.POCKET_CARDS, pocketCardsDTO);
    }

}
