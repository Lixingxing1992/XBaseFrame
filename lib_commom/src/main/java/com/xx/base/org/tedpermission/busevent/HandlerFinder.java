package com.xx.base.org.tedpermission.busevent;

/**
 * Created by Administrator on 2016/11/28.
 */

import java.util.Map;
import java.util.Set;

/** Finds producer and subscriber methods. */
interface HandlerFinder {

    Map<Class<?>, EventProducer> findAllProducers(Object listener);

    Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object listener);


    HandlerFinder ANNOTATED = new HandlerFinder() {
        @Override
        public Map<Class<?>, EventProducer> findAllProducers(Object listener) {
            return AnnotatedHandlerFinder.findAllProducers(listener);
        }

        @Override
        public Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object listener) {
            return AnnotatedHandlerFinder.findAllSubscribers(listener);
        }
    };
}