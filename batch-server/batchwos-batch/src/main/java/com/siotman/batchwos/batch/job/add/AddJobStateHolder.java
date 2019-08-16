package com.siotman.batchwos.batch.job.add;

import com.siotman.batchwos.batch.job.JobStateHolder;
import org.springframework.stereotype.Component;

@Component
public class AddJobStateHolder extends JobStateHolder {
    public AddJobStateHolder() { super("add"); }
}
