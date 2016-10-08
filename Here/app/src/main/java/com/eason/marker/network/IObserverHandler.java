package com.eason.marker.network;

/**
 * Created by Eason on 7/25/15.
 */
public interface IObserverHandler {
    public <T> void response(String response, Class<T> t);
}
