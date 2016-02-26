package com.dianping.jdeferred;

import com.dianping.jdeferred.impl.SimpleDeferred;


/**
 * Deferred interface to trigger an event (resolve, reject).
 * Subsequently, this will allow Promise observers to listen in on the event
 * (done, fail).
 * 
 * @see {@link SimpleDeferred}
 * 
 * @param <R>
 *            Type used for {@link #resolve(Object)}
 */
public interface Deferred<R> extends Promise<R>{
	/**
	 * This should be called when a task has completed successfully.
	 * 
	 * <pre>
	 * <code>
	 * {@link Deferred} deferredObject = new {@link DeferredObject}();
	 * {@link Promise} promise = deferredObject.promise();
	 * promise.doneThen(new {@link Callback}() {
	 *   public void on(Object result) {
	 *   	// Done!
	 *   }
	 * });
	 * 
	 * // another thread using the same deferredObject
	 * deferredObject.resolve("OK");
	 * 
	 * </code>
	 * </pre>
	 * 
	 * @param resolve
	 * @return
	 */
	Deferred<R> resolve(final R result);

	/**
	 * This should be called when a task has completed unsuccessfully, 
	 * i.e., a failure may have occurred.
	 * 
	 * <pre>
	 * <code>
	 * {@link Deferred} deferredObject = new {@link SimpleDeferred}();
	 * {@link Promise} promise = deferredObject.promise();
	 * promise.failThen(new {@link Callback}() {
	 *   public void on(Object result) {
     *      // Done :(
     *   }
	 * });
	 * 
	 * // another thread using the same deferredObject
	 * deferredObject.reject(t);
	 * 
	 * </code>
	 * </pre>
	 * 
	 * @param resolve
	 * @return
	 */
	Deferred<R> reject(final Throwable t);

	/**
	 * Return an {@link Promise} instance (i.e., an observer).  You can register callbacks in this observer.
	 * 
	 * @return
	 */
	Promise<R> promise();
	
}
