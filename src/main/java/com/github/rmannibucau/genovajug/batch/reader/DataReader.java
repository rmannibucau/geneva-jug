package com.github.rmannibucau.genovajug.batch.reader;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.Serializable;
import java.io.StringReader;

@Named
public class DataReader extends AbstractItemReader {
    @Inject
    @BatchProperty
    private String data;
    private BufferedReader reader;

    @Override
    public void open(Serializable serializable) throws Exception {
        reader = new BufferedReader(new StringReader(data));
    }

    @Override
    public Object readItem() throws Exception {
        return reader.readLine();
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
