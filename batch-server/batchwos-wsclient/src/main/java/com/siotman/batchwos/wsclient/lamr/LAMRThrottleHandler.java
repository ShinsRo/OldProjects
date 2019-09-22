package com.siotman.batchwos.wsclient.lamr;

import java.util.concurrent.TimeUnit;

public class LAMRThrottleHandler {
    public final static long MIN_IN_NANOS = TimeUnit.SECONDS.toNanos(60);
    public final static int LAMR_THROTTLE_PER_MIN = 30000;
    private final RequestsPerSec[] minBaseRing = new RequestsPerSec[60];

    private int getIdx(long nowTime) {
        return (int) TimeUnit.NANOSECONDS.toSeconds(nowTime) % 60;
    }

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

    /**
     * nowTime 에 따라 요청량을 누적하거나 갱신한다.
     * <p>
     * 1. 누적하는 경우
     * -   이미 저장된 요청량의 저장 시간으로부터 오프셋이 1초 이내일 경우
     * <p>
     * 2. 갱신하는 경우
     * -   '1.'에 해당하지 않는 경우
     *
     * @param nowTime  현재 시스템 시간 (nano)
     * @param requests 요청량
     */
    public void record(long nowTime, int requests) {
        int idx = getIdx(nowTime);

        RequestsPerSec target = minBaseRing[idx];
        target.save(nowTime, requests);
    }

    private class RequestsPerSec {
        protected long time;
        protected int requests;

        protected boolean isValid(long nowTime) {
            long delta = nowTime - time;
            return delta < MIN_IN_NANOS;
        }

        protected void save(long time, int requests) {
            if (isValid(time)) {
                this.requests += requests;
            } else {
                this.requests = requests;
            }
            this.time = time;
        }
    }

    public <DTO extends Object> void sendDtoByType(DTO dto) {
    }
}
