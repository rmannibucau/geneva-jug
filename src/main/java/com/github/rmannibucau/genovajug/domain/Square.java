package com.github.rmannibucau.genovajug.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(
            name = "Square.findAll",
            query = "select i from Square i"),
    @NamedQuery(
            name = "Square.findAllByParty",
            query = "select i from Square i where i.partyId = :partyId"),
    @NamedQuery(
            name = "Square.findAllByPartyAndPosition",
            query = "select i from Square i where i.partyId = :partyId and i.x = :x and i.y = :y"),
})
public class Square {
    public static enum Player {
        O, X
    }

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private Player player;

    private String partyId;
    private int x;
    private int y;

    public long getId() {
        return id;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(final String partyId) {
        this.partyId = partyId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public static Square newSquare(final String partyId, final Square.Player player, final int x, final int y) {
        final Square newSquare = new Square();
        newSquare.setX(x);
        newSquare.setY(y);
        newSquare.setPartyId(partyId);
        newSquare.setPlayer(player);
        return newSquare;
    }
}
