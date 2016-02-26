package com.dianping.jdeferred;

import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

import com.dianping.jdeferred.impl.MasterDeferred;
import com.dianping.jdeferred.impl.MasterResult;
import com.dianping.jdeferred.impl.OneResult;
import com.dianping.jdeferred.impl.SimpleDeferred;

public class CascadePromiseTest{
        
    @Test
    public void testCascade() {
        final Object[] objs = new Object[3];
        SimpleDeferred<Integer> deferredInt = new SimpleDeferred<Integer>();
        deferredInt.doneThen(new Callback<Integer>() {
            
            @Override
            public void on(Integer object) {
                objs[0] = object;
                System.out.println(object);
            }
        });
        SimpleDeferred<String> deferredStr = new SimpleDeferred<String>();
        deferredStr.doneThen(new Callback<String>() {
            
            @Override
            public void on(String object) {
                objs[1] = object;
                System.out.println(object);
            }
        });
        
        final SimpleDeferred<Long> deferredLng = new SimpleDeferred<Long>();
        new MasterDeferred(deferredInt, deferredStr).doneThen(new Callback<MasterResult>() {
            
            @Override
            public void on(MasterResult masterResult) {
                Iterator<OneResult<?>> iter = masterResult.iterator();
                Long intResult= Long.parseLong((iter.next().getResult()).toString());
                Long strResult = Long.parseLong((iter.next().getResult()).toString());
                deferredLng.resolve(intResult+strResult);
            }
        }).doneCascade(new PromiseCascadable<Long>() {
            @Override
            public Promise<Long> cascade() {
                
                deferredLng.doneThen(new Callback<Long>() {
                    
                    @Override
                    public void on(Long object) {
                        objs[2] = object;
                        System.out.println(object);
                    }
                });
                return deferredLng.promise();
            }
        });        
        deferredStr.resolve("222");
        deferredInt.resolve(111);
        
        Assert.assertNotNull(objs);
        Assert.assertEquals(objs[0], 111);
        Assert.assertEquals(objs[1], "222");
        Assert.assertEquals(objs[2], 333L);
    }
}
