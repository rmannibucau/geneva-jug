package com.github.rmannibucau.genevajug.batch.processor;

import com.github.rmannibucau.genevajug.domain.Square;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

import static java.lang.Integer.parseInt;

@Named
public class SquareProcessor implements ItemProcessor {
    @Override
    public Object processItem(final Object o) throws Exception {
        final String[] parts = o.toString().split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException(o.toString());
        }
        return Square.newSquare(parts[0], Square.Player.valueOf(parts[1]),
                                parseInt(parts[2]), parseInt(parts[3]));
    }
}
