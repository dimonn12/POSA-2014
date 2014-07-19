package edu.vuum.mocca;

import java.util.concurrent.locks.*;

/**
 * @class SimpleSemaphore
 * @brief This class provides a simple counting semaphore
 * implementation using Java a ReentrantLock and a
 * ConditionObject (which is accessed via a Condition). It must
 * implement both "Fair" and "NonFair" semaphore semantics,
 * just liked Java Semaphores.
 */
public class SimpleSemaphore {

    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
    private final ReentrantLock lock;

    /**
     * Define a Condition that waits while the number of permits is 0.
     */
    // TODO - you fill in here
    private final Condition conditionObject;

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
    private volatile int currentPermits;

    public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
        currentPermits = permits;
        lock = new ReentrantLock(fair);
        conditionObject = lock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here.
        lock.lockInterruptibly();
        try {
            while (currentPermits <= 0) {
                conditionObject.await();
            }
            currentPermits--;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here.
        lock.lock();
        try {
            while (currentPermits <= 0) {
                conditionObject.awaitUninterruptibly();
            }
            currentPermits--;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Return one permit to the semaphore.
     */
    public void release() {
        // TODO - you fill in here.
        lock.lock();
        try {
            currentPermits++;
            if (currentPermits > 0) {
                conditionObject.signal();
            } 
        } finally {
            lock.unlock();
        }
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
        // TODO - you fill in here by changing null to the appropriate
        // return value.
        return currentPermits; // You will change this value.
    }
}
