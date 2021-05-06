package ua.knu.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class StringModifier implements Runnable {

    private int id;
    private CyclicBarrier barrier;
    private String str;
    private Result result;

    public StringModifier(int id, String str, CyclicBarrier barrier, Result result) {
        this.id = id;
        this.str = str;
        this.barrier = barrier;
        this.result = result;
    }

    @Override
    public void run() {
        System.out.println("String modifier #" + id + " get string to compute: " + str);
        long aCount = str.chars().filter(ch -> ch == 'A').count();
        long bCount = str.chars().filter(ch -> ch == 'B').count();
        Map<Character, Character> replacement = new HashMap<>();

        if (aCount > bCount) {
            replacement.put('A', 'C');
            replacement.put('D', 'B');

            replace(aCount - bCount, replacement);
        } else {
            replacement.put('B', 'D');
            replacement.put('C', 'A');

            replace(bCount - aCount, replacement);
        }
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("String modifier #" + id + " computed string: " + str);
        result.saveResult("String modifier #" + id + " computed string: " + str);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void replace(long number, Map<Character, Character> replacement) {
        StringBuilder stringBuilder = new StringBuilder(str);
        char current;
        for (int i = 0; i < stringBuilder.length(); i++) {
            current = stringBuilder.charAt(i);
            if (replacement.containsKey(current)) {
                stringBuilder.setCharAt(i, replacement.get(current));
                number--;
                if (number == 0)
                    break;
            }
        }
        str = stringBuilder.toString();
    }
}
