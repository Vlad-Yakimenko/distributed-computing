package com.example.ensign;

import com.example.entity.Stuff;
import com.example.queue.StolenStuff;
import com.example.util.StuffGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Ivanov extends Ensign {

    private final StolenStuff stolenStuff;

    public Ivanov(StolenStuff stolenStuff) {
        this.stolenStuff = stolenStuff;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Stuff stuff = StuffGenerator.generate();
                this.stolenStuff.put(stuff);

                log.info("Ivanov stole {}", stuff);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.warn("Ivanov's stealing process was interrupted");
                break;
            }
        }
    }
}
