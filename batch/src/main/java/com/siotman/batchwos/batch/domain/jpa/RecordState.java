package com.siotman.batchwos.batch.domain.jpa;

public enum RecordState {
    SHOULD_UPDATE, IN_PROGRESS, ERROR, COMPLETED,
    NOT_AVAILABLE, NO_SUBSCRIBE
}
