package com.github.rmannibucau.genovajug.presentation;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ApplicationScoped
public class JSFProducers {
    @Produces
    @RequestScoped
    public ExternalContext externalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }
}
