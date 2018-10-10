package eu.vstoyanov.bitbot.test.exchange;

import static eu.vstoyanov.bitbot.test.exchange.DataSet.*;

/**
 *  This one should have the ability to match the dataset to the current imaginary time
 *
 */
public class Exchange {

    public Exchange(DataSet dataSet) {
        this.dataSet = dataSet;
        this.currentTick = 0;
    }

    public Entry nextTick() {
        return this.dataSet.getNext();
    }

    public void startOver() {
        this.currentTick = 0;
    }

    private Integer currentTick;

    private DataSet dataSet;
}
