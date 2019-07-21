package com.siotman.batchwos.batch.job.add;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TempResourcesResolver {
    @Autowired
    private ResourceLoader resourceLoader;

    public Resource[] loadResources(String path) {
        try {
            return ResourcePatternUtils
                    .getResourcePatternResolver(resourceLoader)
                    .getResources(path);
        } catch (IOException e) {
            return new Resource[]{};
        }
    }
}
