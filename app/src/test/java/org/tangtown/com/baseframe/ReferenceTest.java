package org.tangtown.com.baseframe;

import android.support.annotation.IntDef;
import org.junit.Test;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author Lixingxing
 */
public class ReferenceTest {

    @Test
    public void testPhantomReference(){
        // 虚引用：不会影响到对象的生命周期,方便调用者跟踪对象被GC的回收

        ReferenceQueue<Object> objectReferenceQueue = new ReferenceQueue<>();
        Object obj = new Object();

        PhantomReference phantomReference = new PhantomReference(obj,objectReferenceQueue);
        System.out.println("PhantomReference:" + phantomReference.get());
        System.out.println("phantomReference:" + objectReferenceQueue.poll());

        obj = null;
        System.gc();
        try {
            Thread.sleep(2000);
            System.out.println("PhantomReference:" + phantomReference.get());
            System.out.println("phantomReference:" + objectReferenceQueue.poll());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testWeakReference(){
        // 软引用: 第一次扫到了，就标记下来，第二次扫到直接回收
        ReferenceQueue<Object> objectReferenceQueue = new ReferenceQueue<>();
        Object obj = new Object();

        WeakReference weakReference = new WeakReference(obj,objectReferenceQueue);
        System.out.println("WeakReference:" + weakReference.get());
        System.out.println("phantomReference:" + objectReferenceQueue.poll());

        obj = null;
        System.gc();
        try {
            Thread.sleep(2000);
            System.out.println("WeakReference:" + weakReference.get());
            System.out.println("phantomReference:" + objectReferenceQueue.poll());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


//    public enum SHAPE{
//        rectangle,
//        triangle,
//        souare,
//        circle
//    }


    public static class SHAPE{
        public static final int rectangle = 0;
        public static final int triangle = 1;
        public static final int souare = 2;
        public static final int circle = 3;

        // 自定义标签
        @Documented
        //flag=true代表输入的值可以用|, value代表只能用这四个值
        @IntDef(flag=true,value={rectangle,triangle,souare,circle})
        //存活时间
        @Retention(RetentionPolicy.SOURCE)
        public @interface Model{

        }
        private  @Model int value = rectangle;
        public void setShape(@Model int model){
            this.value = model;
        }
        @Model
        public int getShape(){
            return value;
        }
    }

}
