package com.github.rmannibucau.genevajug.service;

import com.github.rmannibucau.genevajug.domain.Square;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;

@Singleton
@Lock(LockType.READ)
public class TicTacToeService {
    @PersistenceContext(unitName = "tic-tac-toe")
    private EntityManager em;

    @Inject
    private Integer size;

    public Square play(final String partyId) {
        return computerMove(partyId);
    }

    private Square computerMove(final String partyId) {
        final Square[][] table = gameAsArray(partyId);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (table[i][j] == null) {
                    return Square.newSquare(partyId, Square.Player.O, i + 1, j + 1);
                }
            }
        }
        return null;
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public boolean hasPlayer(final String partyId, final int x, final int y) {
        try {
            final Square square = em.createNamedQuery("Square.findAllByPartyAndPosition", Square.class)
                    .setParameter("partyId", partyId)
                    .setParameter("x", x)
                    .setParameter("y", y)
                    .getSingleResult();
            return square.getPlayer() != null;
        } catch (final NoResultException nre) {
            return false;
        }
    }

    public boolean hasWon(final Square square) {
        final Square.Player player = square.getPlayer();
        final int x = square.getX() - 1;
        final int y = square.getY() - 1;

        final Square[][] array = gameAsArray(square.getPartyId());

        for (int i = 0; i < size; i++) { // vertical
            if (!isTheSamePlayer(player, array[x][i])) {
                break;
            }
            if (i == size - 1) {
                return true;
            }
        }
        for (int i = 0; i < size; i++) { // horizontal
            if (!isTheSamePlayer(player, array[i][y])) {
                break;
            }
            if (i == size - 1) {
                return true;
            }
        }
        if (x == y) { // diagonal
            for (int i = 0; i < size; i++) {
                if (!isTheSamePlayer(player, array[i][i])) {
                    break;
                }
                if (i == size - 1) {
                    return true;
                }
            }
        } else if (x == size - y - 1) { // anti-diagonal
            for (int i = 0; i < size; i++) {
                final int xtmp = size - i - 1;
                if (!isTheSamePlayer(player, array[xtmp][i])) {
                    break;
                }
                if (i == size - 1) {
                    return true;
                }
            }
        }

        return false;
    }

    public void savePosition(final Square square) {
        em.persist(square);
    }

    private boolean isTheSamePlayer(final Square.Player player, final Square square) {
        return square != null && square.getPlayer() != null && square.getPlayer().equals(player);
    }

    public Square[][] gameAsArray(final String partyId) {
        final List<Square> entities = em.createNamedQuery("Square.findAllByParty", Square.class)
                .setParameter("partyId", partyId)
                .getResultList();

        final Square[][] table = new Square[size][];
        for (int i = 0; i < size; i++) {
            table[i] = new Square[size];
        }

        if (entities != null) {
            for (final Square s : entities) {
                table[s.getX() - 1][s.getY() - 1] = s;
            }
        }

        return table;
    }

    public Collection<Square> findAll() {
        final List<Square> list = em.createNamedQuery("Square.findAll", Square.class).getResultList();
        if (list == null) {
            return emptyList();
        }
        return list;
    }
}
