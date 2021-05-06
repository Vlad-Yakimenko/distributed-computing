package com.example.queue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractQueue<T> {

    private T stuff;

    private boolean isValueSet = false;

    private final String implementation;

    public synchronized void put(T stuff) throws InterruptedException {
        while (isValueSet) {
            wait();
        }

        this.stuff = stuff;
        isValueSet = true;
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while (!isValueSet) {
            wait();
        }

        isValueSet = false;
        notifyAll();
        return this.stuff;
    }
}
