package com.flexpoker.core.pot;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.flexpoker.model.Game;
import com.flexpoker.model.Hand;
import com.flexpoker.model.Pot;
import com.flexpoker.model.Seat;
import com.flexpoker.model.Table;
import com.flexpoker.test.util.datageneration.DeckGenerator;
import com.flexpoker.test.util.datageneration.GameGenerator;

public class CalculatePotsAfterRoundImplQueryTest {

    private CalculatePotsAfterRoundImplQuery query;

    @Before
    public void setup() {
        query = new CalculatePotsAfterRoundImplQuery();
    }
    
    @Test
    public void testCalculatePotsAfterRound() {
        Game game = GameGenerator.createGame(9, 9);
        Table table = new Table();

        testCalculatePotsAfterRound1(game, table);
        testCalculatePotsAfterRound2(game, table);
        testCalculatePotsAfterRound3(game, table);
        testCalculatePotsAfterRound4(game, table);
    }

    private void testCalculatePotsAfterRound1(Game game, Table table) {
        Seat seat1 = new Seat(0);
        seat1.setChipsInFront(30);
        seat1.setStillInHand(true);

        table.addSeat(seat1);
        
        table.setCurrentHand(new Hand(new ArrayList<Seat>(),
                DeckGenerator.createDeck()));

        Set<Pot> pots = query.execute(table);
        Pot pot = ((Pot) pots.toArray()[0]);

        assertEquals(1, pots.size());
        assertEquals(30, pot.getAmount());
        assertTrue(pot.getSeats().contains(seat1));
    }

    private void testCalculatePotsAfterRound2(Game game, Table table) {
        Seat seat1 = new Seat(0);
        seat1.setChipsInFront(30);
        seat1.setStillInHand(true);
        Seat seat2 = new Seat(1);
        seat2.setChipsInFront(30);
        seat2.setStillInHand(true);

        table.getSeats().clear();
        
        table.addSeat(seat1);
        table.addSeat(seat2);
        
        Set<Pot> pots = query.execute(table);
        Pot pot = ((Pot) pots.toArray()[0]);

        assertEquals(1, pots.size());
        assertEquals(60, pot.getAmount());
        assertTrue(pot.isOpen());
        assertTrue(pot.getSeats().contains(seat1));
        assertTrue(pot.getSeats().contains(seat2));
    }

    private void testCalculatePotsAfterRound3(Game game, Table table) {
        Seat seat1 = new Seat(0);
        seat1.setChipsInFront(30);
        seat1.setStillInHand(true);
        Seat seat2 = new Seat(1);
        seat2.setChipsInFront(30);
        seat2.setStillInHand(true);
        seat2.setAllIn(true);

        table.getSeats().clear();
        
        table.addSeat(seat1);
        table.addSeat(seat2);

        Set<Pot> pots = query.execute(table);
        Pot pot = ((Pot) pots.toArray()[0]);

        assertEquals(1, pots.size());
        assertEquals(60, pot.getAmount());
        assertFalse(pot.isOpen());
        assertTrue(pot.getSeats().contains(seat1));
        assertTrue(pot.getSeats().contains(seat2));
    }

    private void testCalculatePotsAfterRound4(Game game, Table table) {
        Seat seat1 = new Seat(0);
        seat1.setChipsInFront(30);
        seat1.setStillInHand(true);
        Seat seat2 = new Seat(1);
        seat2.setChipsInFront(30);
        seat2.setStillInHand(true);
        Seat seat3 = new Seat(2);
        seat3.setChipsInFront(30);
        seat3.setStillInHand(true);
        Seat seat4 = new Seat(3);
        seat4.setChipsInFront(30);
        seat4.setStillInHand(true);

        table.getSeats().clear();
        
        table.addSeat(seat1);
        table.addSeat(seat2);
        table.addSeat(seat3);
        table.addSeat(seat4);

        // simulate preflop
        Set<Pot> pots = query.execute(table);
        table.getCurrentHand().setPots(pots);
        Pot pot = ((Pot) pots.toArray()[0]);

        assertEquals(1, pots.size());
        assertEquals(120, pot.getAmount());
        assertTrue(pot.isOpen());
        assertTrue(pot.getSeats().contains(seat1));
        assertTrue(pot.getSeats().contains(seat2));
        assertTrue(pot.getSeats().contains(seat3));
        assertTrue(pot.getSeats().contains(seat4));

        seat1.setChipsInFront(50);
        seat2.setChipsInFront(50);
        seat3.setChipsInFront(50);
        seat4.setChipsInFront(50);

        // simulate preturn
        pots = query.execute(table);
        table.getCurrentHand().setPots(pots);
        pot = ((Pot) pots.toArray()[0]);
        
        assertEquals(1, pots.size());
        assertEquals(320, pot.getAmount());
        assertTrue(pot.isOpen());
        assertTrue(pot.getSeats().contains(seat1));
        assertTrue(pot.getSeats().contains(seat2));
        assertTrue(pot.getSeats().contains(seat3));
        assertTrue(pot.getSeats().contains(seat4));

        seat1.setChipsInFront(20);
        seat1.setAllIn(true);
        seat2.setChipsInFront(40);
        seat2.setAllIn(true);
        seat3.setChipsInFront(90);
        seat4.setChipsInFront(90);

        // simulate preriver
        pots = query.execute(table);
        table.getCurrentHand().setPots(pots);
        
        Pot pot1 = null;
        Pot pot2 = null;
        Pot pot3 = null;
        
        for(Pot loopPot : pots) {
            switch (loopPot.getAmount()) {
            case 400:
                pot1 = loopPot;
                break;
            case 60:
                pot2 = loopPot;
                break;
            case 100:
                pot3 = loopPot;
                break;
            }
        }
        
        assertEquals(3, pots.size());

        assertEquals(400, pot1.getAmount());
        assertFalse(pot1.isOpen());
        assertTrue(pot1.getSeats().contains(seat1));
        assertTrue(pot1.getSeats().contains(seat2));
        assertTrue(pot1.getSeats().contains(seat3));
        assertTrue(pot1.getSeats().contains(seat4));

        assertEquals(60, pot2.getAmount());
        assertFalse(pot2.isOpen());
        assertFalse(pot2.getSeats().contains(seat1));
        assertTrue(pot2.getSeats().contains(seat2));
        assertTrue(pot2.getSeats().contains(seat3));
        assertTrue(pot2.getSeats().contains(seat4));

        assertEquals(100, pot3.getAmount());
        assertTrue(pot3.isOpen());
        assertFalse(pot3.getSeats().contains(seat1));
        assertFalse(pot3.getSeats().contains(seat2));
        assertTrue(pot3.getSeats().contains(seat3));
        assertTrue(pot3.getSeats().contains(seat4));

        seat3.setChipsInFront(100);
        seat3.setAllIn(true);
        seat4.setChipsInFront(350);

        // simulate last round
        pots = query.execute(table);
        table.getCurrentHand().setPots(pots);

        pot1 = null;
        pot2 = null;
        pot3 = null;
        Pot pot4 = null;
        
        for(Pot loopPot : pots) {
            switch (loopPot.getAmount()) {
            case 400:
                pot1 = loopPot;
                break;
            case 60:
                pot2 = loopPot;
                break;
            case 300:
                pot3 = loopPot;
                break;
            case 250:
                pot4 = loopPot;
                break;
            }
        }
        
        assertEquals(4, pots.size());
        
        assertEquals(400, pot1.getAmount());
        assertFalse(pot1.isOpen());
        assertTrue(pot1.getSeats().contains(seat1));
        assertTrue(pot1.getSeats().contains(seat2));
        assertTrue(pot1.getSeats().contains(seat3));
        assertTrue(pot1.getSeats().contains(seat4));
        
        assertEquals(60, pot2.getAmount());
        assertFalse(pot2.isOpen());
        assertFalse(pot2.getSeats().contains(seat1));
        assertTrue(pot2.getSeats().contains(seat2));
        assertTrue(pot2.getSeats().contains(seat3));
        assertTrue(pot2.getSeats().contains(seat4));
        
        assertEquals(300, pot3.getAmount());
        assertFalse(pot3.isOpen());
        assertFalse(pot3.getSeats().contains(seat1));
        assertFalse(pot3.getSeats().contains(seat2));
        assertTrue(pot3.getSeats().contains(seat3));
        assertTrue(pot3.getSeats().contains(seat4));
        
        assertEquals(250, pot4.getAmount());
        assertTrue(pot4.isOpen());
        assertFalse(pot4.getSeats().contains(seat1));
        assertFalse(pot4.getSeats().contains(seat2));
        assertFalse(pot4.getSeats().contains(seat3));
        assertTrue(pot4.getSeats().contains(seat4));
    }

}
