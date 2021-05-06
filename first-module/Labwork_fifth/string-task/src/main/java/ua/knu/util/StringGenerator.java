package ua.knu.util;

import java.util.Random;

public class StringGenerator {

    private final static char[] chars = {'A', 'B', 'C', 'D'};
    private final static Random random = new Random();
    private final static int max_length = 100;

    static public String generateString() {
        int length = random.nextInt(max_length);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(chars[random.nextInt(chars.length)]);
        }
        return result.toString();
    }
}
