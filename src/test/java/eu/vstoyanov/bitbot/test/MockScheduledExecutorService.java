package eu.vstoyanov.bitbot.test;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * A mock for execution service in order to speed up integration testing (simulate ticks).
 * Instead of respecting the schedule it could and needs to be forced to run immediately.
 *
 */
public class MockScheduledExecutorService implements ScheduledExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(MockScheduledExecutorService.class);

    public MockScheduledExecutorService() {
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {

        this.currentCommand = command;

        return new MockScheduledFutureTask<>(() -> "Done;");
    }

    public void forceRun() {
        currentCommand.run();
    }

    private Runnable currentCommand;

    private static class MockScheduledFutureTask<T> extends FutureTask<T> implements ScheduledFuture<T> {

        public MockScheduledFutureTask(Callable<T> callable) {
            super(callable);
        }

        public MockScheduledFutureTask(Runnable runnable, T result) {
            super(runnable, result);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return 0;
        }

        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }

    // NOT IMPLEMENTED

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public void shutdown() {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public List<Runnable> shutdownNow() {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public boolean isShutdown() {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public boolean isTerminated() {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public Future<?> submit(Runnable task) {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }

    @Override
    public void execute(Runnable command) {
        throw new NotImplementedException("This is a test mock, the method is not yet implemented");
    }
}
