package com.github.rmannibucau.genovajug.batch.writer;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Named
public class SquareWriter extends AbstractItemWriter {
    @PersistenceContext(unitName = "tic-tac-toe")
    private EntityManager em;

    @Override
    public void writeItems(final List<Object> objects) throws Exception {
        for (final Object o : objects) {
            em.persist(o);
        }
    }
}
