package eu.vstoyanov.bitbot.test;

import org.apache.commons.lang3.NotImplementedException;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

/**
 * A mock implementation of {@link java.time.Clock } - allows the time to be rolled forward programatically in order to
 * avoid waiting for ticks during testing.
 */
public class MutableClock extends Clock {

    public MutableClock(Instant beginningOfTime) {
        this.beginningOfTime = beginningOfTime;
        this.offset = Duration.ZERO;
    }

    @Override
    public synchronized Instant instant() {
        return beginningOfTime.plus(offset);
    }

    public synchronized void rollForward(Duration duration) {
        offset = offset.plus(duration);
    }

    private Instant beginningOfTime;

    private Duration offset;

    // NOT IMPLEMENTED

    @Override
    public ZoneId getZone() {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public Clock withZone(ZoneId zone) {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

}
