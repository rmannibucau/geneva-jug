package com.github.rmannibucau.genovajug.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class Configuration {
    @Produces
    public Integer size() {
        return 3;
    }
}
