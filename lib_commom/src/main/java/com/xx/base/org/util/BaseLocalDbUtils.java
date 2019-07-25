package com.xx.base.org.util;

import android.content.Context;
import io.paperdb.Paper;

/**
 * 本地化数据存储 使用 Paper
 * Created by lixingxing
 */
public class BaseLocalDbUtils {
    public static void init(Context context){
        // 初始化nosql数据库
        Paper.init(context);
    }
    /**********************read***********************/
    public static <T> T readDefault(String name){
        return read("default",name);
    }
    public static <T> T readDefaultWithDefault(String name,Object defaultVaule){
        return readWithDefault("default",name,defaultVaule);
    }
    public static <T> T read(String name){
        return  read("",name);
    }
    public static <T> T read(String namespace,String name){
        T value = (T) (("".equals(namespace)|| null == namespace)?(Paper.book().read(name)):(Paper.book(namespace).read(name)));
        return value;
    }
    public static <T> T readWithDefault(String name,Object defaultValue){
        return readWithDefault("",name,defaultValue);
    }
    public static <T> T readWithDefault(String namespace,String name,Object defaultValue){
        T value = (T) (("".equals(namespace)|| null == namespace)?(Paper.book().read(name,defaultValue)):(Paper.book(namespace).read(name,defaultValue)));
        return value;
    }

    /**********************write***********************/
    public static void writeDefault(String key,Object value){
        write("default",key,value);
    }
    public static void write(String key,Object value){
        write("",key,value);
    }
    public static void write(String namespace,String key,Object value){
        if(value != null){
            if (("".equals(namespace) || null == namespace)) {
                Paper.book().write(key, value);
            } else {
                Paper.book(namespace).write(key, value);
            }
        }
    }

    /**********************remove****************************/
    public static void removeDefault(String key){
        remove("default",key);
    }
    public static void remove(String key){
        remove("",key);
    }
    public static void remove(String namespace,String key){
        if (("".equals(namespace) || null == namespace)) {
            Paper.book().delete(key);
        } else {
            Paper.book(namespace).delete(key);
        }
    }

}
