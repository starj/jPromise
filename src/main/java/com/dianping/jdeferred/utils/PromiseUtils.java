package com.dianping.jdeferred.utils;

import com.dianping.jdeferred.Promise;
import com.dianping.jdeferred.impl.SimpleDeferred;

public class PromiseUtils {
    public static<T> Promise<T> getResolvedPromise(T result) {
        return new SimpleDeferred<T>().resolve(result).promise();
    }
    
    public static<T> Promise<T> getRejectedPromise(Throwable rejection) {
        return new SimpleDeferred<T>().reject(rejection).promise();
    }
}
