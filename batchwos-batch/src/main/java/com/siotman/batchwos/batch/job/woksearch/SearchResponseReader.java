package com.siotman.batchwos.batch.job.woksearch;

import com.siotman.batchwos.wsclient.search.domain.SearchResponse;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.Serializable;

public class SearchResponseReader implements ItemReader<SearchResponse> {
    @Override
    public SearchResponse read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
