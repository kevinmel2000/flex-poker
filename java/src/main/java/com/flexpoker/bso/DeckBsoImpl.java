package com.flexpoker.bso;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.flexpoker.model.Card;
import com.flexpoker.model.CardRank;
import com.flexpoker.model.CardSuit;
import com.flexpoker.model.Deck;
import com.flexpoker.model.FlopCards;
import com.flexpoker.model.PocketCards;
import com.flexpoker.model.RiverCard;
import com.flexpoker.model.Table;
import com.flexpoker.model.TurnCard;
import com.flexpoker.model.User;

import edu.emory.mathcs.backport.java.util.Arrays;

@Service("deckBso")
public class DeckBsoImpl implements DeckBso {

    private Map<Table, Deck> tableToDeckMap = new HashMap<Table, Deck>();

    private final List<Card> cardList;

    @SuppressWarnings("unchecked")
    public DeckBsoImpl() {
        cardList = Arrays.asList(new Card[]{
            new Card(0, CardRank.TWO, CardSuit.HEARTS),
            new Card(1, CardRank.THREE, CardSuit.HEARTS),
            new Card(2, CardRank.FOUR, CardSuit.HEARTS),
            new Card(3, CardRank.FIVE, CardSuit.HEARTS),
            new Card(4, CardRank.SIX, CardSuit.HEARTS),
            new Card(5, CardRank.SEVEN, CardSuit.HEARTS),
            new Card(6, CardRank.EIGHT, CardSuit.HEARTS),
            new Card(7, CardRank.NINE, CardSuit.HEARTS),
            new Card(8, CardRank.TEN, CardSuit.HEARTS),
            new Card(9, CardRank.JACK, CardSuit.HEARTS),
            new Card(10, CardRank.QUEEN, CardSuit.HEARTS),
            new Card(11, CardRank.KING, CardSuit.HEARTS),
            new Card(12, CardRank.ACE, CardSuit.HEARTS),
            new Card(13, CardRank.TWO, CardSuit.SPADES),
            new Card(14, CardRank.THREE, CardSuit.SPADES),
            new Card(15, CardRank.FOUR, CardSuit.SPADES),
            new Card(16, CardRank.FIVE, CardSuit.SPADES),
            new Card(17, CardRank.SIX, CardSuit.SPADES),
            new Card(18, CardRank.SEVEN, CardSuit.SPADES),
            new Card(19, CardRank.EIGHT, CardSuit.SPADES),
            new Card(20, CardRank.NINE, CardSuit.SPADES),
            new Card(21, CardRank.TEN, CardSuit.SPADES),
            new Card(22, CardRank.JACK, CardSuit.SPADES),
            new Card(23, CardRank.QUEEN, CardSuit.SPADES),
            new Card(24, CardRank.KING, CardSuit.SPADES),
            new Card(25, CardRank.ACE, CardSuit.SPADES),
            new Card(26, CardRank.TWO, CardSuit.DIAMONDS),
            new Card(27, CardRank.THREE, CardSuit.DIAMONDS),
            new Card(28, CardRank.FOUR, CardSuit.DIAMONDS),
            new Card(28, CardRank.FIVE, CardSuit.DIAMONDS),
            new Card(30, CardRank.SIX, CardSuit.DIAMONDS),
            new Card(31, CardRank.SEVEN, CardSuit.DIAMONDS),
            new Card(32, CardRank.EIGHT, CardSuit.DIAMONDS),
            new Card(33, CardRank.NINE, CardSuit.DIAMONDS),
            new Card(34, CardRank.TEN, CardSuit.DIAMONDS),
            new Card(35, CardRank.JACK, CardSuit.DIAMONDS),
            new Card(36, CardRank.QUEEN, CardSuit.DIAMONDS),
            new Card(37, CardRank.KING, CardSuit.DIAMONDS),
            new Card(38, CardRank.ACE, CardSuit.DIAMONDS),
            new Card(39, CardRank.TWO, CardSuit.CLUBS),
            new Card(40, CardRank.THREE, CardSuit.CLUBS),
            new Card(41, CardRank.FOUR, CardSuit.CLUBS),
            new Card(42, CardRank.FIVE, CardSuit.CLUBS),
            new Card(43, CardRank.SIX, CardSuit.CLUBS),
            new Card(44, CardRank.SEVEN, CardSuit.CLUBS),
            new Card(45, CardRank.EIGHT, CardSuit.CLUBS),
            new Card(46, CardRank.NINE, CardSuit.CLUBS),
            new Card(47, CardRank.TEN, CardSuit.CLUBS),
            new Card(48, CardRank.JACK, CardSuit.CLUBS),
            new Card(49, CardRank.QUEEN, CardSuit.CLUBS),
            new Card(50, CardRank.KING, CardSuit.CLUBS),
            new Card(51, CardRank.ACE, CardSuit.CLUBS)
        });
    }

    @Override
    public void shuffleDeck(Table table) {
        Deck deck = null;

        synchronized (this) {
            try {
                Thread.currentThread().sleep(10);
                Collections.shuffle(cardList, new Random());
                deck = new Deck(cardList, table);
            } catch (InterruptedException e) {
                throw new RuntimeException("InterruptedException thrown while "
                        + "generating random number.");
            }
        }

        synchronized (tableToDeckMap) {
            tableToDeckMap.put(table, deck);
        }

    }

    @Override
    public void removeDeck(Table table) {
        synchronized (tableToDeckMap) {
            tableToDeckMap.remove(table);
        }
    }

    @Override
    public FlopCards fetchFlopCards(Table table) {
        return tableToDeckMap.get(table).getFlopCards();
    }

    @Override
    public PocketCards fetchPocketCards(User user, Table table) {
        return tableToDeckMap.get(table).getPocketCards(user);
    }

    @Override
    public RiverCard fetchRiverCard(Table table) {
        return tableToDeckMap.get(table).getRiverCard();
    }

    @Override
    public TurnCard fetchTurnCard(Table table) {
        return tableToDeckMap.get(table).getTurnCard();
    }

}
