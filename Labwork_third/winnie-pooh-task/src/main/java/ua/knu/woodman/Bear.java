package ua.knu.woodman;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class Bear implements Runnable {

    private AtomicInteger honeyPot;
    private Integer maxVolume;

    @Override
    public void run() {
        if (honeyPot.compareAndSet(maxVolume, 0)) {
            log.info("Bear successfully ate all honey");
        } else {
            throw new IllegalStateException("Something went wrong!");
        }
    }
}
