package eu.vstoyanov.bitbot.test.exchange;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * this one should have access to instant and get the current one
 */
public class DataSet {

    public static final DataSet JPY = new DataSet("bitflyerJPY_1-min_data_2017-07-04_to_2018-06-27.csv");

    private final Logger logger = Logger.getLogger(DataSet.class.getName());
    private static final String CSV_SEPARATOR = ",";

    public static class Entry {

        Entry(Instant instant, BigDecimal weightedPrice) {
            this.instant = instant;
            this.weightedPrice = weightedPrice;
        }

        private Instant instant;

        private BigDecimal weightedPrice;

        public Instant getInstant() {
            return instant;
        }

        public BigDecimal getWeightedPrice() {
            return weightedPrice;
        }
    }

    private DataSet(String filename) {
        Path path;
        try {
            path = Paths.get(getClass().getClassLoader().getResource(filename).toURI());
            logger.fine(String.format("Started reading dataset: %s", path.toString()));
            Stream<String> lines = Files.lines(path);
            boolean first = true;
            lines.skip(1)
                    .forEach(line -> {

                        String[] elements = line.split(CSV_SEPARATOR);
                        entries.add(new Entry(
                                Instant.ofEpochSecond(Long.parseLong(elements[0])),
                                new BigDecimal(elements[7])));
            });
            logger.fine(String.format("Finished reading dataset: %s", path.toString()));

        } catch (URISyntaxException | IOException e) {
            logger.log(Level.SEVERE,"Error reading dataset", e);
        }

        iterator = entries.listIterator();
    }

    public Entry getNext() {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            System.exit(0);
            return null;
        }
    }

    public Entry getPrevious() {
        return iterator.previous();
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    private final List<Entry> entries = new LinkedList<>();
    private final ListIterator<Entry> iterator;
}

