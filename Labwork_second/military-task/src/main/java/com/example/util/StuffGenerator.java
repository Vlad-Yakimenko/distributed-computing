package com.example.util;

import com.example.entity.Stuff;

import java.time.LocalDateTime;
import java.util.Random;

public class StuffGenerator {

    private static final Random random = new Random();

    private StuffGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static Stuff generate() {
        return Stuff.builder()
                .name(String.format("Stuff-%s", LocalDateTime.now()))
                .cost(random.nextInt(100))
                .build();
    }
}
