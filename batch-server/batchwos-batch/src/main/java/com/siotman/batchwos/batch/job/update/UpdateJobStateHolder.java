package com.siotman.batchwos.batch.job.update;

import com.siotman.batchwos.batch.component.SocketSessionContainer;
import com.siotman.batchwos.batch.job.JobStateHolder;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class UpdateJobStateHolder extends JobStateHolder {
    @Autowired SocketSessionContainer sessionContainer;

    public UpdateJobStateHolder() {
        super("update");
    }

    public void increaseElement(String elementName, Integer amount) {
        Map<String, Object> result = super.getStepResult("fetchAndUpdate");

        Integer element = (result.containsKey(elementName)) ?
                (Integer) result.get(elementName) + amount : amount;

        result.put(elementName, element);

        String LOG_MSG = String.format(
                "[%s] increaseElement RESULT: %s",
                "3030", describeStepResultAsString("fetchAndUpdate")
        );
        logger.info(LOG_MSG);
        try {
            sessionContainer.broadcast(
                    new JSONObject(new HashMap<String, Object>() {{
                        put("type", "increaseElement");
                        put("elementName", elementName);
                        put("amount", amount);
                    }}).toString()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
