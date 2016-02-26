package com.dianping.jdeferred;
/**
 * 
 * CallbackException
 * 
 */
public class CallbackException extends RuntimeException{
    
    private static final long serialVersionUID = -556303360327116841L;
    
    public CallbackException(Exception t) {
        super(t);
    }
}
