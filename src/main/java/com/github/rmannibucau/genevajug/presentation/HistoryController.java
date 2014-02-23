package com.github.rmannibucau.genevajug.presentation;

import com.github.rmannibucau.genevajug.domain.Square;
import com.github.rmannibucau.genevajug.service.TicTacToeService;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

@Named("history")
@ApplicationScoped
public class HistoryController {
    @Inject
    private ExternalContext externalContext;

    @Inject
    private TicTacToeService service;

    public Collection<Square> getHistory() {
        return service.findAll();
    }

    public Square[][] getPartyHistory() {
        final String partyId = externalContext.getRequestParameterMap().get("partyId");
        return service.gameAsArray(partyId);
    }
}
