package com.dianping.jdeferred.impl;

import com.dianping.jdeferred.Promise;

public class OneResult<R> {
    private final int index;
	private final Promise<R> promise;
	private final R result;
	
	public OneResult(int index, Promise<R> promise, R result) {
		this.index = index;
	    this.promise = promise;
		this.result = result;
	}

	public Promise<R> getPromise() {
		return promise;
	}
	
	public Object getResult() {
		return result;
	}
	
	public int getIndex() {
	    return index;
	}
	
	@Override
	public String toString() {
		return "OneResult [index=" + index + " ,promise=" + promise + ", result=" + result + "]";
	}
}
