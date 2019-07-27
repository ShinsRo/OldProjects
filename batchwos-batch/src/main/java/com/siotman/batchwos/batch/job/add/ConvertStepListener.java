package com.siotman.batchwos.batch.job.add;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.siotman.batchwos.batch.common.CONSTANTS.FILE_SEP;
import static com.siotman.batchwos.batch.common.CONSTANTS.RESOURCE_DIR;

@Component
public class ConvertStepListener implements StepExecutionListener, ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(ConvertStepListener.class);

    String XML_RESOURCE_PATH = String.format("file:./%s/fetched_*.xml".replaceAll("/", FILE_SEP), RESOURCE_DIR);

    @Autowired private ResourceLoader resourceLoader;

    private ApplicationContext applicationContext;
    private Resource[] xmlResources;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info("[0200] Before ConvertStep, setting Resources");
        MultiResourceItemReader reader =
                (MultiResourceItemReader) applicationContext.getBean("convertStepReader");
        xmlResources = getResources(XML_RESOURCE_PATH);
        reader.setResources(xmlResources);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }

    private Resource[] getResources(String path) {
        try {
            return ResourcePatternUtils
                    .getResourcePatternResolver(resourceLoader)
                    .getResources(path);
        } catch (IOException e) {
            return new Resource[]{};
        }
    }
}
