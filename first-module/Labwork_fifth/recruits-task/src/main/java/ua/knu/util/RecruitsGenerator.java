package ua.knu.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecruitsGenerator {

    private static Random random = new Random();

    public static List<Boolean> generate() {
        int size = random.nextInt(900) + 100;
        ArrayList<Boolean> array = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            array.add(random.nextBoolean());
        }
        return array;
    }
}
