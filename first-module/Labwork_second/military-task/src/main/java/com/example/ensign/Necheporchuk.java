package com.example.ensign;

import com.example.queue.Truck;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Necheporchuk extends Ensign {

    private final Truck truck;
    private int sum;

    public Necheporchuk(Truck truck) {
        this.truck = truck;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                this.sum += this.truck.get().getCost();

                Thread.sleep(100);
                log.info("Necheporchuk recounted total gain, it is {}$", this.sum);
            } catch (InterruptedException e) {
                log.warn("Necheporchuk's stealing process was interrupted");
                break;
            }
        }
    }
}
