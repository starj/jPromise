package com.dianping.jdeferred.impl;

import com.dianping.jdeferred.Callback;
import com.dianping.jdeferred.Deferred;
import com.dianping.jdeferred.Promise;



/**
 * An implementation of {@link Deferred} interface.
 * 
 * <pre>
 * <code>
 * final {@link Deferred} deferredObject = new {@link SimpleDeferred}
 * 
 * {@link Promise} promise = deferredObject.promise();
 * promise
 *   .then(new Callback() { ... });
 *   
 * {@link Runnable} runnable = new {@link Runnable}() {
 *   public void run() {
 *     int sum = 0;
 *     for (int i = 0; i < 100; i++) {
 *       // something that takes time
 *       sum += i;
 *       deferredObject.notify(i);
 *     }
 *     deferredObject.resolve(sum);
 *   }
 * }
 * // submit the task to run
 * 
 * </code>
 * </pre>
 * 
 * @see Callback
 */
public class SimpleDeferred<R> extends SimplePromise<R> implements Deferred<R> {
    
	@Override
	public Deferred<R> resolve(final R resolve) {
		synchronized (this) {
			if (!this.isPending())
				throw new IllegalStateException("Deferred object already finished, cannot resolve again");
			
			this.state = State.RESOLVED;
			this.resolveResult = resolve;
			
			try {
			    this.triggerDone(resolve);
			} finally {
				
			}
		}
		return this;
	}

	@Override
	public Deferred<R> reject(final Throwable reject) {
		synchronized (this) {
			if (!this.isPending())
				throw new IllegalStateException("Deferred object already finished, cannot reject again");
			this.state = State.REJECTED;
			this.rejectResult = reject;
			
			try {
			    this.triggerFail(reject);
			} finally {
				
			}
		}
		return this;
	}

	@Override
    public Promise<R> promise(){
	    return this;
	}
}
