package ua.knu.human;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class Client implements Runnable {

    private long id;
    private Barber barber;
    private Semaphore barberChair;
    private Semaphore waitingChairs;

    @Override
    public void run() {
        if (waitingChairs.tryAcquire()) {
            log.info("Client {} waiting", id);

            while (!barberChair.tryAcquire()) {
                barber.wakeUp();
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            log.info("Client {} set in barber chair", id);
            waitingChairs.release();

        } else {
            log.info("No free sits");
        }
    }
}
