package com.dianping.jpromise.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.dianping.jpromise.Callback;
import com.dianping.jpromise.Promise;

/**
 * This will return a special Promise called {@link MasterDeferred}. In short,
 * <ul>
 * <li>{@link Promise#doneCascade(DoneCallback)} will be triggered if all promises
 * resolves (i.e., all finished successfully) with {@link MasterResult}.</li>
 * </ul>
 */
public class MasterDeferred extends SimpleDeferred<MasterResult> {
    private final int numberOfPromises;
    private final AtomicInteger doneCount = new AtomicInteger();
    private final MasterResult masterResult;

    @SuppressWarnings("unchecked")
    public MasterDeferred(Promise<?>... promises) {
        if (promises == null || promises.length == 0)
            throw new IllegalArgumentException("Promises is null or empty");
        this.numberOfPromises = promises.length;
        this.masterResult = new MasterResult(numberOfPromises);
        int i=0;
        for (final Promise<?> promise : promises) {
            final int index = i++;
            final Promise<Object> promiseObj = (Promise<Object>) promise;
            promiseObj.doneThen(new Callback<Object>() {               
                @SuppressWarnings("rawtypes")
                @Override
                public void on(final Object result) {
                    synchronized (MasterDeferred.this) {
                        if (!MasterDeferred.this.isPending()) return;
                    }
                    masterResult.add(new OneResult(index, promiseObj, result));
                    if (doneCount.incrementAndGet() == numberOfPromises) {
                        MasterDeferred.this.resolve(masterResult);
                    }
                }
            });

            promise.failThen(new Callback<Throwable>() {
                @Override
                public void on(Throwable reject) {
                    synchronized (MasterDeferred.this) {
                        if (!MasterDeferred.this.isPending())
                            return;
                        MasterDeferred.this.reject(reject);
                    }
                }
            });
        }
    }
}
