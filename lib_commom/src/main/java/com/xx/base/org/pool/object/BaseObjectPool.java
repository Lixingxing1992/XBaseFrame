package com.xx.base.org.pool.object;

import android.util.SparseArray;

/**
 * 对象池
 * @author Lixingxing
 */
public abstract class BaseObjectPool<T> {
    protected abstract T create();

    //空闲池，用户从这个里面拿对象
    private SparseArray<T> freePool;
    //正在使用池，用户正在使用的对象放在这个池记录
    private SparseArray<T> lentPool;

    //池的最大值
    private int maxCapacity;

    public BaseObjectPool(int initialCapacity, int maxCapacity) {
        //初始化对象池
        initalize(initialCapacity);
        this.maxCapacity=maxCapacity;
    }
    public BaseObjectPool(int maxCapacity) {
        this(maxCapacity/2,maxCapacity);
    }

    private void initalize(int initialCapacity) {
        lentPool=new SparseArray<>();
        freePool=new SparseArray<>();
        for(int i=0;i<initialCapacity;i++){
            freePool.put(i,create());
        }
    }

    /**
     * 申请对象
     * @return
     */
    public T acquire() throws Exception {
        T t=null;
        synchronized (freePool){
            int freeSize=freePool.size();
            for(int i=0;i<freeSize;i++){
                int key=freePool.keyAt(i);
                t=freePool.get(key);
                if(t!=null){
                    this.lentPool.put(key,t);
                    this.freePool.remove(key);
                    return t;
                }
            }
            //如果没对象可取了
            if(t==null && lentPool.size()+freeSize<maxCapacity){
                //这里可以自己处理,超过大小
                if(lentPool.size()+freeSize==maxCapacity){
                    throw new Exception();
                }
                t=create();
                lentPool.put(lentPool.size()+freeSize,t);
            }
        }
        return t;
    }

    /**
     * 回收对象
     * @return
     */
    public void release(T t){
        if(t==null){
            return;
        }
        int key=lentPool.indexOfValue(t);
        //释放前可以把这个对象交给用户处理
        restore(t);

        this.freePool.put(key,t);
        this.lentPool.remove(key);

    }

    protected void restore(T t){

    }

}
