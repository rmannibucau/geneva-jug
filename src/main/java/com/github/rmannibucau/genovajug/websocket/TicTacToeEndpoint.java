package com.github.rmannibucau.genovajug.websocket;

import com.github.rmannibucau.genovajug.domain.Square;
import com.github.rmannibucau.genovajug.service.TicTacToeService;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

@ServerEndpoint("/tic-tac-toe")
public class TicTacToeEndpoint {
    private static final Logger LOGGER = Logger.getLogger(TicTacToeEndpoint.class.getName());

    private static enum MoveState {
        MOVE, WRONG_POSITION, WIN, EGALITY;

        public String error(final String input) {
            return name() + " " + input;
        }

        public String move(final Square square) {
            return name() + " " + square.getX() + " " + square.getY() + " " + square.getPlayer().name();
        }
    }

    @Inject
    private TicTacToeService ticTacToeService;

    @OnOpen
    public void onOpen(final Session session) {
        LOGGER.info("Open " + session);
    }

    @OnMessage // TODO: better tx handling
    public String onMessage(final String message, final Session session) throws IOException {
        final String partyId = session.getId();

        // let client valid its movement and save ikt
        final Square clientMove = move(partyId, message);
        if (clientMove == null) {
            return MoveState.WRONG_POSITION.error(message);
        }
        ticTacToeService.savePosition(clientMove);
        if (ticTacToeService.hasWon(clientMove)) {
            return MoveState.WIN.move(clientMove);
        }
        session.getBasicRemote().sendText(MoveState.MOVE.move(clientMove));

        // server move
        final Square square = ticTacToeService.play(partyId);
        if (square == null) {
            return MoveState.EGALITY.name();
        }
        ticTacToeService.savePosition(square);
        if (ticTacToeService.hasWon(square)) {
            return MoveState.WIN.move(square);
        }
        return MoveState.MOVE.move(square);
    }

    @OnClose
    public void onClose(final Session session) {
        LOGGER.info("Close " + session);
    }

    private Square move(final String id, final String message) {
        final String[] to = message.split("_");
        if (to.length != 2) {
            return null;
        }

        final int[] pos = new int[]{parseInt(to[0]), parseInt(to[1])};
        if (validateMove(id, pos[0], pos[1])) {
            return Square.newSquare(id, Square.Player.X, pos[0], pos[1]);
        }
        return null;
    }

    private boolean validateMove(final String partyId, final int x, final int y) {
        return x > 0 && x < 4 && y > 0 && y < 4
                && !ticTacToeService.hasPlayer(partyId, x, y);

    }
}
