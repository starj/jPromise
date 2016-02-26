package com.dianping.jdeferred.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.dianping.jdeferred.Callback;
import com.dianping.jdeferred.CallbackException;
import com.dianping.jdeferred.Promise;
import com.dianping.jdeferred.PromiseCascadable;

/**
 *
 * @see Promise
 *
 */
public class SimplePromise<R> implements Promise<R> {

	protected volatile State state = State.PENDING;

	protected final List<Callback<R>> doneCallbacks = new CopyOnWriteArrayList<Callback<R>>();
	protected final List<Callback<Throwable>> failCallbacks = new CopyOnWriteArrayList<Callback<Throwable>>();
	
	protected R resolveResult;
	protected Throwable rejectResult;

	@Override
	public State state() {
		return state;
	}
	
	protected void triggerDone(R resolved) {
		for (Callback<R> callback : doneCallbacks) {
			try {
				triggerDone(callback, resolved);
			} catch (Exception e) {
			    throw new CallbackException(e);
			}
		}
	}
	
	protected void triggerDone(Callback<R> callback, R resolved) {
		callback.on(resolved);
	}
	
	protected void triggerFail(Throwable rejected) {
		for (Callback<Throwable> callback : failCallbacks) {
			try {
				triggerFail(callback, rejected);
			} catch (Exception e) {
			    throw new CallbackException(e);
			}
		}
	}
	
	protected void triggerFail(Callback<Throwable> callback, Throwable rejected) {
		callback.on(rejected);
	}
	
	
	@Override
	public Promise<R> doneThen(Callback<R> callback) {
	    synchronized (this) {
            doneCallbacks.add(callback);
            if (isResolved()) triggerDone(callback, resolveResult);
        }
        return this;
	}

    @Override
    public Promise<R> failThen(Callback<Throwable> callback) {
        synchronized (this) {
            failCallbacks.add(callback);
            if (isRejected()) triggerFail(callback, rejectResult);
        }
        return this;
    }
    
    @Override
    public <Q> Promise<Q> doneCascade(final PromiseCascadable<Q> pipe) {
        final SimpleDeferred<Q> chained = new SimpleDeferred<Q>();
        this.doneThen(new Callback<R>() {
            @Override
            public void on(final R resultR) {
                if(pipe != null) {
                    Promise<Q> promiseQ = pipe.cascade();
                    promiseQ.doneThen(new Callback<Q>() {
                        @Override
                        public void on(Q resultQ) {
                            chained.resolve(resultQ);
                        }
                    });
                } else {
                    chained.resolve(null);
                }
            }
        });
        return chained.promise();
    }
	
	@Override
	public boolean isPending() {
		return state == State.PENDING;
	}

	@Override
	public boolean isResolved() {
		return state == State.RESOLVED;
	}

	@Override
	public boolean isRejected() {
		return state == State.REJECTED;
	}
	
}
