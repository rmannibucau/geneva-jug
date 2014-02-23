package com.github.rmannibucau.genovajug.presentation;

import com.github.rmannibucau.genovajug.domain.Square;
import com.github.rmannibucau.genovajug.service.TicTacToeService;

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
