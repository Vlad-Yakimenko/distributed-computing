package com.example;

import com.example.ensign.Ivanov;
import com.example.ensign.Necheporchuk;
import com.example.ensign.Petrov;
import com.example.queue.StolenStuff;
import com.example.queue.Truck;

public class Main {

    public static void main(String[] args) {
        StolenStuff stolenStuff = new StolenStuff();
        Truck truck = new Truck();
        Ivanov ivanov = new Ivanov(stolenStuff);
        Petrov petrov = new Petrov(truck, stolenStuff);
        Necheporchuk necheporchuk = new Necheporchuk(truck);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ivanov.finishPlunder();
        petrov.finishPlunder();
        necheporchuk.finishPlunder();
    }
}
