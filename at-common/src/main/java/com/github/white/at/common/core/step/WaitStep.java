package com.github.white.at.common.core.step;

import java.util.concurrent.TimeUnit;

public class WaitStep implements Step {

    private final TimeUnit timeUnit;

    private final long timeout;

    public WaitStep(long timeout, TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        this.timeout = timeout;
    }

    @Override
    public void execute() {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
