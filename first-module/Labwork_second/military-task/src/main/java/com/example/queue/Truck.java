package com.example.queue;

import com.example.entity.Stuff;

public class Truck extends AbstractQueue<Stuff> {
    public Truck() {
        super(Truck.class.getSimpleName());
    }
}
