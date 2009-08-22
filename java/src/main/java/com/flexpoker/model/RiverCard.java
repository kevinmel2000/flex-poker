package com.flexpoker.model;

public class RiverCard {

    private Card card;

    public RiverCard(Card card) {
        this.card = card;
    }

    public RiverCard() {}

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

}
