package ua.knu.monitor;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import ua.knu.GardenSimulator;

import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class FileMonitor implements Runnable {

    private GardenSimulator gardenSimulator;
    private ReentrantReadWriteLock.ReadLock readLock;

    @Override
    @SneakyThrows
    @SuppressWarnings("BusyWait")
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            readLock.lock();


            try {
                File file = FileUtils.getFile("./garden-task/src/main/resources/log.txt");
                StringBuilder gardenRepresentation = new StringBuilder("***-BEGIN OF GARDEN-***\n");
                IntStream.range(0, 10).forEach(x -> {
                    IntStream.range(0, 10).forEach(y -> gardenRepresentation.append(gardenSimulator.getPlant(Pair.of(x, y))));
                    gardenRepresentation.append('\n');
                });
                gardenRepresentation.append("***-END OF GARDEN-***\n");
                FileUtils.writeStringToFile(file, gardenRepresentation.toString(), Charset.defaultCharset(), true);

            } finally {
                readLock.unlock();
            }

            try {
                Thread.sleep(650);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }
}
