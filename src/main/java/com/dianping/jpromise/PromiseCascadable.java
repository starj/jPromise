package com.dianping.jpromise;

import java.nio.channels.Pipe;


/**
 * @see Promise#doneCascade(Pipe)
 *
 * @param <Q> Type of the output from this filter
 */
public interface PromiseCascadable<Q> {
	public Promise<Q> cascade();
}
