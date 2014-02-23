package com.github.rmannibucau.genovajug.presentation;

import com.github.rmannibucau.genovajug.domain.Square;
import com.github.rmannibucau.genovajug.service.TicTacToeService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("ticTacToe")
@ApplicationScoped
public class TicTacToeController {
    private static final String ID_PREFIX = "ttt";

    @Inject
    private Integer size;

    private Square[][] defaultTable;

    @Inject
    private TicTacToeService service;

    @PostConstruct
    private void init() {
        defaultTable = new Square[size][];
        for (int i = 0; i < defaultTable.length; i++) {
            defaultTable[i] = new Square[size];
            for (int j = 0; j < defaultTable[i].length; j++) {
                final int x = i + 1;
                final int y = j + 1;
                defaultTable[i][j] = Square.newSquare(null, null, x, y);
            }
        }
    }

    public Square[][] getDefaultTable() {
        return defaultTable;
    }
}
