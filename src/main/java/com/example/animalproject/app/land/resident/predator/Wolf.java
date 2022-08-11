package com.example.animalproject.app.land.resident.predator;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.herbivore.*;

import java.util.concurrent.atomic.AtomicInteger;


public class Wolf extends Predator {

    static volatile AtomicInteger count = new AtomicInteger(0);

    static {
        UtilAnimal.putMapAmountAnimal(Wolf.class, 30);
        Wolf.chanceSuccessfulHunt.put(Horse.class, 30);
        Wolf.chanceSuccessfulHunt.put(Deer.class, 35);
        Wolf.chanceSuccessfulHunt.put(Rabbit.class, 50);
        Wolf.chanceSuccessfulHunt.put(Mouse.class, 60);
        Wolf.chanceSuccessfulHunt.put(Goat.class, 60);
        Wolf.chanceSuccessfulHunt.put(Sheep.class, 70);
        UtilAnimal.putSpeedAnimal(Wolf.class, 3);
        UtilAnimal.putAbilityToReproduce(Wolf.class, 1);
    }


    public Wolf() {
        Wolf.count.incrementAndGet();
        this.weight = 50;
        this.icon = "\uD83D\uDC3B";
        this.foodConsumption = 8;
        this.degreeOfSaturation = 0;
    }


    public static AtomicInteger getCount() {
        return count;
    }

    public static void setCount(AtomicInteger count) {
        Wolf.count = count;
    }

    public void decrement() {
        Wolf.count.decrementAndGet();
        Predator.count.decrementAndGet();
        Animal.getCount().decrementAndGet();
    }

    @Override
    public Animal madeNewAnimal() {
        return new Wolf();
    }


    @Override
    public String toString() {
        return "Wolf{" +
                "icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
