package com.zemoga.porfolio.adapters.gateways;

public interface Gateways<T,R> {
    T consumeService(R request);
}