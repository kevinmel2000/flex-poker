package com.flexpoker.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.flexpoker.model.FlopCards;
import com.flexpoker.model.Game;
import com.flexpoker.model.PocketCards;
import com.flexpoker.model.RiverCard;
import com.flexpoker.model.Table;
import com.flexpoker.model.TurnCard;
import com.flexpoker.model.UserGameStatus;

public interface FlexController {

    List<Game> fetchAllGames();

    void createGame(Game game);

    void joinGame(Game game);

    Set<UserGameStatus> fetchAllUserGameStatuses(Game game);

    void verifyRegistrationForGame(Game game);

    PocketCards fetchPocketCards(Table table);

    Table fetchTable(Game game);

    void verifyGameInProgress(Game game);

    void check(Table table);

    FlopCards fetchFlopCards(Table table);

    TurnCard fetchTurnCard(Table table);

    RiverCard fetchRiverCard(Table table);

    Map<Integer, PocketCards> fetchRequiredShowCards(Table table);

    Map<Integer, PocketCards> fetchOptionalShowCards(Table table);
}
