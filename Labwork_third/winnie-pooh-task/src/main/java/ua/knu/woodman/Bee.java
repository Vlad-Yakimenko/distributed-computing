package ua.knu.woodman;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class Bee implements Runnable {

    private Integer id;
    private CyclicBarrier barrier;
    private AtomicInteger honeyPot;
    private Integer maxVolume;

    @Override
    @SneakyThrows
    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    public void run() {
        while (true) {
            int currentVolume = honeyPot.get();
            if (currentVolume < maxVolume) {

                if (honeyPot.compareAndSet(currentVolume, currentVolume + 1)) {
                    log.info("Bee {} put honey in the honey pot, current volume: {}", id, currentVolume + 1);
                }
                Thread.sleep(200);

            } else if (currentVolume == maxVolume) {
                barrier.await();

            } else {
                throw new IllegalStateException("Something went wrong!");
            }
        }
    }
}
