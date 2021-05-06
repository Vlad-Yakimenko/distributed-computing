package ua.knu.changer;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.tuple.Pair;
import ua.knu.GardenSimulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class Gardener implements Runnable {

    private GardenSimulator gardenSimulator;
    private ReentrantReadWriteLock.WriteLock writeLock;
    private Random random = new Random();

    @Override
    @SuppressWarnings("BusyWait")
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            writeLock.lock();

            try {
                val currentGarden = gardenSimulator.getGarden();
                val witheredPlants = getWitheredPlants(currentGarden);

                witheredPlants.stream()
                        .filter(x -> random.nextBoolean())
                        .forEach(index -> gardenSimulator.changePlantState(
                                index.getLeft(),
                                index.getRight(),
                                2));

                log.info("Gardener poured plants");

            } finally {
                writeLock.unlock();
            }

            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private Collection<Pair<Integer, Integer>> getWitheredPlants(Integer[][] garden) {
        val indexesOfWitheredPlants = new ArrayList<Pair<Integer, Integer>>();

        IntStream.range(0, 10).forEach(x -> IntStream.range(0, 10).forEach(y -> {
            if (garden[x][y] == 1) {
                indexesOfWitheredPlants.add(Pair.of(x, y));
            }
        }));

        return indexesOfWitheredPlants;
    }
}
