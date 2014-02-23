package com.github.rmannibucau.genovajug.batch;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class JBatchProducers {
    @Produces
    public JobOperator operator() {
        return BatchRuntime.getJobOperator();
    }
}
