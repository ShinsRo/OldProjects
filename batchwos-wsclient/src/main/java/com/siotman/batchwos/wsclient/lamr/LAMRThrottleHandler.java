package com.siotman.batchwos.wsclient.lamr;

import java.util.concurrent.TimeUnit;

public class LAMRThrottleHandler {
    public final int LAMR_THROTTLE_PER_MIN = 30000;
    private final RequestsPerSec[] minBaseRing = new RequestsPerSec[60];

    public LAMRThrottleHandler() {
        for (int i = 0; i < minBaseRing.length; i++) {
            minBaseRing[i] = new RequestsPerSec();
        }
    }

    public boolean test(long nowTime, int waitingAmount) {
        int accumulator = 0;

        for (RequestsPerSec rps : minBaseRing) {
            if (!rps.isValid(nowTime)) continue;
            accumulator += rps.requests;
        }

        return accumulator + waitingAmount < LAMR_THROTTLE_PER_MIN;
    }

    public void recode(long nowTime, int requestedAmount) {
        long idx = TimeUnit.NANOSECONDS.toSeconds(nowTime) % 60;

        RequestsPerSec target = minBaseRing[(int) idx];
        target.save(nowTime, requestedAmount);
    }

    private class RequestsPerSec {
        protected long time;
        protected int requests;

        protected boolean isValid(long nowTime) {
            long delta = nowTime - time;
            return delta < TimeUnit.SECONDS.toNanos(60);
        }

        protected void save(long time, int requests) {
            this.time = time;
            if (isValid(time)) {
                this.requests += requests;
            } else {
                this.requests = 0;
            }
        }
    }
}
