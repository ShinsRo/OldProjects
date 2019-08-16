package com.siotman.batchwos.batch.job.update;

import com.siotman.batchwos.batch.job.JobStateHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UpdateJobStateHolder extends JobStateHolder {
    public UpdateJobStateHolder() {
        super("update");
    }

    public void increaseElement(String elementName, Integer amount) {
        Map<String, Object> result = super.getStepResult("fetchAndUpdate");

        Integer element = (result.containsKey(elementName)) ?
                (Integer) result.get(elementName) + amount : amount;

        result.put(elementName, element);
    }
}
