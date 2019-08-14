package com.siotman.batchwos.batch.job;

import org.springframework.stereotype.Component;

@Component
public class UpdateJobStateHolder extends JobStateHolder{
    public UpdateJobStateHolder() {
        super("update");
    }
}
