package com.siotman.wos.jaxws2rest.component;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncRunner {
    @Async
    public void run(Runnable runnable) {
        runnable.run();
    }
}
