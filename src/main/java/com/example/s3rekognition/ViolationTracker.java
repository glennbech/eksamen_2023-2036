package com.example.s3rekognition;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class ViolationTracker {

    private final AtomicInteger currentViolationCount = new AtomicInteger(0);

    public void incrementViolationCount() {
        currentViolationCount.incrementAndGet();
    }

    public void decrementViolationCount() {
        currentViolationCount.decrementAndGet();
    }

    public int getCurrentViolationCount() {
        return currentViolationCount.get();
    }
}

