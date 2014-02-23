package com.github.rmannibucau.genevajug.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class Configuration {
    @Produces
    public Integer size() {
        return 3;
    }
}
