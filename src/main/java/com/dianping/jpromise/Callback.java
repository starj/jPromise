package com.dianping.jpromise;


/**
 * @see Deferred#resolve(Object)
 * @see Promise#doneThen(Callback)
 *
 * @param <E>
 */
public interface Callback<E> {
	public void on(final E object);
}
