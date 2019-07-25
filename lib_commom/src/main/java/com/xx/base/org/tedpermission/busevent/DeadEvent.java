package com.xx.base.org.tedpermission.busevent;

/**
 * Created by Administrator on 2016/11/28.
 */

/**
 * Wraps an event that was posted, but which had no subscribers and thus could not be delivered.
 *
 * <p>Subscribing a DeadEvent handler is useful for debugging or logging, as it can detect misconfigurations in a
 * system's event distribution.
 *
 * @author Cliff Biffle
 */
public class DeadEvent {

    public final Object source;
    public final Object event;

    /**
     * Creates a new DeadEvent.
     *
     * @param source object broadcasting the DeadEvent (generally the {@link com.squareup.otto.Bus}).
     * @param event the event that could not be delivered.
     */
    public DeadEvent(Object source, Object event) {
        this.source = source;
        this.event = event;
    }

}