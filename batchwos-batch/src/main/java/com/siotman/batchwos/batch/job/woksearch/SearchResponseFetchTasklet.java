package com.siotman.batchwos.batch.job.woksearch;

import com.siotman.batchwos.wsclient.search.SearchClient;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatCallback;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.File;

public class SearchResponseFetchTasklet implements Tasklet, InitializingBean {
    private SearchClient searchClient;
    private Resource directory;
    private String SID;
    private Long queryId;
    private Long firstRecord;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        File dir = directory.getFile();
        Assert.state(dir.isDirectory());


        return null;
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        RepeatTemplate template = new RepeatTemplate();

        template.setCompletionPolicy(new SimpleCompletionPolicy(2));

        template.iterate(context -> {

            return RepeatStatus.CONTINUABLE;
        });

    }
}
