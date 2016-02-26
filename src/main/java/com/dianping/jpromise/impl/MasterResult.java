package com.dianping.jpromise.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Contains a list of {@link OneResult}.
 *
 */
public class MasterResult implements Iterable<OneResult<?>> {
	private final List<OneResult<?>> results;
	
	public MasterResult(int size) {
	    this.results = new CopyOnWriteArrayList<OneResult<?>>(new ArrayList<OneResult<?>>(size));
	}
	
	protected void add(OneResult<?> result) {
		results.add(result);
	}
	
	public OneResult<?> get(int index) {
		return results.get(index);
	}

	@Override
    public Iterator<OneResult<?>> iterator() {
		return results.iterator();
	}
	
	public int size() {
		return results.size();
	}

	@Override
	public String toString() {
		return "MasterResult [results=" + results + "]";
	}
}
