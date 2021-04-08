package ua.knu;

import ua.knu.util.Result;
import ua.knu.util.StringGenerator;
import ua.knu.util.StringModifier;

import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) {
        final int threadsNumber = 4;
        CyclicBarrier barrier = new CyclicBarrier(3);
        String tmp;
        for (int i = 0; i < threadsNumber; i++) {
            tmp = StringGenerator.generateString();
            Thread thread = new Thread(new StringModifier(i, tmp, barrier, Result.getInstance()));
            thread.start();
        }
    }
}
