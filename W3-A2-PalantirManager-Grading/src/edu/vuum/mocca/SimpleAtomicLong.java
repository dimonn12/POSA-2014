package edu.vuum.mocca;

import java.util.concurrent.locks.*;

/**
 * @class SimpleAtomicLong
 * @brief This class implements a subset of the
 * java.util.concurrent.atomic.SimpleAtomicLong class using a
 * ReentrantReadWriteLock to illustrate how they work.
 */
class SimpleAtomicLong {

    /**
     * The value that's manipulated atomically via the methods.
     */
    private volatile long mValue;

    /**
     * The ReentrantReadWriteLock used to serialize access to mValue.
     */

    // TODO -- you fill in here by replacing the null with an
    // initialization of ReentrantReadWriteLock.
    private final ReentrantReadWriteLock mRWLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.WriteLock wLock = mRWLock.writeLock();

    /**
     * Creates a new SimpleAtomicLong with the given initial value.
     */
    public SimpleAtomicLong(final long initialValue) {
        // TODO -- you fill in here
        this.mValue = initialValue;

    }

    /**
     * @brief Gets the current value.
     * @returns The current value
     */
    public long get() {
        // TODO -- you fill in here
        return mValue;
    }

    /**
     * @brief Atomically decrements by one the current value
     * @returns the updated value
     */
    public long decrementAndGet() {
        // TODO -- you fill in here
        wLock.lock();
        try {
            return --mValue;
        } finally {
            wLock.unlock();
        }
    }

    /**
     * @brief Atomically increments by one the current value
     * @returns the previous value
     */
    public long getAndIncrement() {
        // TODO -- you fill in here
        wLock.lock();
        try {
            return mValue++;
        } finally {
            wLock.unlock();
        }
    }

    /**
     * @brief Atomically decrements by one the current value
     * @returns the previous value
     */
    public long getAndDecrement() {
        // TODO -- you fill in here
        wLock.lock();
        try {
            return mValue--;
        } finally {
            wLock.unlock();
        }
    }

    /**
     * @brief Atomically increments by one the current value
     * @returns the updated value
     */
    public long incrementAndGet() {
        // TODO -- you fill in here
        wLock.lock();
        try {
            return ++mValue;
        } finally {
            wLock.unlock();
        }
    }
}

