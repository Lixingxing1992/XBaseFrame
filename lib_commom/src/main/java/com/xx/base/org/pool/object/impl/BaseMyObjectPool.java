package com.xx.base.org.pool.object.impl;

import com.xx.base.org.pool.object.BaseObjectPool;

/**
 * 对象池实现类
 * @author Lixingxing
 */
public class BaseMyObjectPool extends BaseObjectPool<Object> {
    public BaseMyObjectPool(int initialCapacity, int maxCapacity) {
        super(initialCapacity, maxCapacity);
    }
    public BaseMyObjectPool(int maxCapacity) {
        super(maxCapacity);
    }
    @Override
    protected Object create() {
        return null;
    }
}
