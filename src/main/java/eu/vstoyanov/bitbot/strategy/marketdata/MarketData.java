package eu.vstoyanov.bitbot.strategy.marketdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class MarketData {

    @Autowired
    public MarketData(Clock clock) {
        lastHourlyUpdate = Instant.now(clock);
    }

    public void record(Instant timestamp, double price) {

        currentTick++;

        lastHourTicks[currentTick] = price;

        if (needsToRollover(timestamp)) {
            rollover(timestamp);
            didUpdate = true;
        } else {
            didUpdate = false;
        }

    }

    public boolean didUpdate() {
        return didUpdate;
    }

    public double[] getHourlyMeans() {
        return hourlyAverages;
    }

    private boolean needsToRollover(Instant timestamp) {
        return timestamp
                .minus(1, ChronoUnit.HOURS)
                .isAfter(lastHourlyUpdate);
    }

    private void rollover(Instant timestamp) {
        System.arraycopy(hourlyAverages, 1, hourlyAverages, 0, hourlyAverages.length - 1);
        hourlyAverages[23] = computeHourlyAverage();

        lastHourlyUpdate = timestamp;
        currentTick = 0;
    }

    private double computeHourlyAverage() {
        double avg = 0;
        int t = 1;
        for (int i = 0; i < currentTick; i++) {
            avg += (lastHourTicks[i] - avg) / t;
            ++t;
        }
        return avg;
    }

    private double[] hourlyAverages = new double[24];

    private double[] lastHourTicks = new double [60 * 60 + 10];

    private int currentTick = 0;

    private boolean didUpdate = false;

    private Instant lastHourlyUpdate = null;

}
