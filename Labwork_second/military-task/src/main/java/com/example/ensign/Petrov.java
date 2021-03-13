package com.example.ensign;

import com.example.entity.Stuff;
import com.example.queue.StolenStuff;
import com.example.queue.Truck;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Petrov extends Ensign {

    private final Truck truck;
    private final StolenStuff stolenStuff;

    public Petrov(Truck truck, StolenStuff stolenStuff) {
        this.truck = truck;
        this.stolenStuff = stolenStuff;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Stuff stuff = this.stolenStuff.get();

                Thread.sleep(100);
                this.truck.put(stuff);

                log.info("Petrov moved {} to a truck", stuff);
            } catch (InterruptedException e) {
                log.warn("Petrov's stealing process was interrupted");
                break;
            }
        }
    }
}
