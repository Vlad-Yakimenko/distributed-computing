package com.example.queue;

import com.example.entity.Stuff;

public class StolenStuff extends AbstractQueue<Stuff> {
    public StolenStuff() {
        super(StolenStuff.class.getSimpleName());
    }
}
