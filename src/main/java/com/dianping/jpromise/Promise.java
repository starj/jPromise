package com.dianping.jpromise;


/**
 * Promise interface to observe when some action has occurred on the
 * corresponding {@link Deferred} object.
 * 
 * A promise object should be obtained from {@link Deferred#promise()), or by
 * using DeferredManager.
 * 
 * <pre>
 * <code>
 * Deferred deferredObject = new DeferredObject(); Promise promise = deferredObject.promise(); promise.done(new DoneCallback()
 * public void onDone(Object result) // Done! } });
 * 
 * // another thread using the same deferredObject deferredObject.resolve("OK");
 * 
 * </code> </pre>
 * 
 * @see Deferred#resolve(Object)
 * @see Deferred#reject(Object)
 * 
 * @param <R>
 *            Type used for {@link #doneThen(Callback)}
 */
public interface Promise<R> {
    public enum State {
        /**
         * The Promise is still pending - it could be created, submitted for
         * execution, or currently running, but not yet finished.
         */
        PENDING,

        /**
         * The Promise has finished running and a failure occurred. Thus, the
         * Promise is rejected.
         * 
         * @see Deferred#reject(Object)
         */
        REJECTED,

        /**
         * The Promise has finished running successfully. Thus the Promise is
         * resolved.
         * 
         * @see Deferred#resolve(Object)
         */
        RESOLVED
    }
    public State state();

    /**
     * @see State#PENDING
     * @return
     */
    public boolean isPending();

    /**
     * @see State#RESOLVED
     * @return
     */
    public boolean isResolved();

    /**
     * @see State#REJECTED
     * @return
     */
    public boolean isRejected();

    /**
     * This method will register a {@link Callback} so that when a Deferred
     * object is resolved ({@link Deferred#resolve(Object)}) or rejected (
     * {@link Deferred#reject(Object)}), {@link Callback} will be triggered.
     * 
     * You can register multiple {@link Callback} by calling the method multiple
     * times. The order of callback trigger is based on the order you call this
     * method.
     * 
     * <pre>
     * <code>
     * promise.doneThen(new Callback(){
     *   public void onTrigger(Object done) {
     *     ...
     *   }
     * });
     * </code>
     * </pre>
     * 
     * @see Deferred#resolve(Object)
     * @see Deferred#reject(Object)
     * @param callback {@link #Callback}
     * @return
     */
    public Promise<R> doneThen(Callback<R> callback);
    
    public Promise<R> failThen(Callback<Throwable> callback);
    
    public<Q> Promise<Q> doneCascade(PromiseCascadable<Q> cascadable);

}
