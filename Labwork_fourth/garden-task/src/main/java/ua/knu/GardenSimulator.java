package ua.knu;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Random;
import java.util.stream.IntStream;

public class GardenSimulator {

    private final Integer[][] garden;
    private final Random random = new Random();

    public GardenSimulator() {
        this.garden = IntStream.range(0, 10)
                .mapToObj(x -> IntStream.range(0, 10)
                        .mapToObj(y -> random.nextInt(3))
                        .toArray(Integer[]::new))
                .toArray(Integer[][]::new);

        IntStream.range(0, 10).forEach(x -> {
            IntStream.range(0, 10).forEach(y -> {
                System.out.print(garden[x][y] + " ");
            });
            System.out.println();
        });

        System.out.println("*******************************");
    }

    public void changePlantState(int x, int y, int newValue) {
        this.garden[x][y] = newValue;
    }

    public Integer[][] getGarden() {
        return this.garden.clone();
    }

    public String getPlant(Pair<Integer, Integer> index) {
        switch (this.garden[index.getLeft()][index.getRight()]) {
            case 0:
                return "0 ";
            case 1:
                return "+ ";
            default:
                return "* ";
        }
    }
}
