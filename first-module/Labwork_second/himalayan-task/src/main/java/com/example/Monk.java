package com.example;

import lombok.Data;

import java.util.Random;

@Data
public class Monk {

    private final String monastery;
    private final int energy;

    public Monk(String monastery) {
        this.monastery = monastery;
        this.energy = new Random().nextInt(100);
    }

    public static Monk max(Monk first, Monk second) {
        return first.getEnergy() > second.getEnergy() ? first : second;
    }
}
