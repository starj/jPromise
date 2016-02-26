package com.dianping.jpromise.utils;

import com.dianping.jpromise.Promise;
import com.dianping.jpromise.impl.SimpleDeferred;

public class PromiseUtils {
    public static<T> Promise<T> getResolvedPromise(T result) {
        return new SimpleDeferred<T>().resolve(result).promise();
    }
    
    public static<T> Promise<T> getRejectedPromise(Throwable rejection) {
        return new SimpleDeferred<T>().reject(rejection).promise();
    }
}
