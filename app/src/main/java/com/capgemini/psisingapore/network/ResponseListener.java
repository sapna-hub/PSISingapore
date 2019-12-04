package com.capgemini.psisingapore.network;

/**
 * Listener to listen to sucess and error cases of network calls.
 * @param <T>
 */
public interface ResponseListener<T> {
     void getResult(T object);
     void onErrorResponse(String errMsg);
}
