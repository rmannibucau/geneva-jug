package com.github.rmannibucau.genova.service;

import com.github.rmannibucau.genevajug.domain.Square;
import com.github.rmannibucau.genevajug.service.TicTacToeService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class HistoryTest {
    @Deployment
    public static Archive<?> jar() {
        return ShrinkWrap.create(JavaArchive.class, "geneva-jug.jar")
                .addPackage(TicTacToeService.class.getPackage())
                .addPackage(Square.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private TicTacToeService service;

    @Test
    public void play() {
        assertNotNull(service);
        assertFalse(service.hasPlayer("foo", 1, 1));
    }
}
