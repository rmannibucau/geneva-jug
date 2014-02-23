package com.github.rmannibucau.genovajug.presentation;

import javax.batch.operations.JobOperator;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

@Named("importHistory")
@RequestScoped
public class ImportHistoryController {
    @Inject
    private JobOperator operator;

    private String data;

    public String doImport() {
        final Properties config = new Properties();
        config.setProperty("data", data);
        operator.start("history-import", config);
        return "index?faces-redirect=true";
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }
}
