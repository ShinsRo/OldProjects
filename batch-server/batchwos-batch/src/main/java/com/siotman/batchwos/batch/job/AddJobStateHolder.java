package com.siotman.batchwos.batch.job;

import org.springframework.stereotype.Component;

@Component
public class AddJobStateHolder extends JobStateHolder{
    public AddJobStateHolder() { super("add"); }
}
