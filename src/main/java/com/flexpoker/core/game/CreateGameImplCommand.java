package com.flexpoker.core.game;

import java.security.Principal;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.context.ApplicationEventPublisher;

import com.flexpoker.config.Command;
import com.flexpoker.core.api.game.CreateGameCommand;
import com.flexpoker.dto.CreateGameDto;
import com.flexpoker.event.GameListUpdatedEvent;
import com.flexpoker.model.Game;
import com.flexpoker.model.User;
import com.flexpoker.repository.api.GameRepository;
import com.flexpoker.repository.api.UserRepository;

@Command
public class CreateGameImplCommand implements CreateGameCommand {

    private final UserRepository userRepository;
    
    private final GameRepository gameRepository;
    
    private final ApplicationEventPublisher applicationEventPublisher;
    
    @Inject
    public CreateGameImplCommand(UserRepository userRepository,
            GameRepository gameRepository,
            ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }
    
    @Override
    public void execute(Principal principal, CreateGameDto gameDto) {
        User user = userRepository.findByUsername(principal.getName());

        Game game = new Game(gameDto.getName(), new Date(), user, new Date(),
                gameDto.getPlayers(), gameDto.getPlayersPerTable(), false);
        gameRepository.saveNew(game);
        
        applicationEventPublisher.publishEvent(new GameListUpdatedEvent(this));
    }

}