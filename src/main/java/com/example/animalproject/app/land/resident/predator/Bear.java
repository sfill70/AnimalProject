package com.example.animalproject.app.land.resident.predator;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.herbivore.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Bear extends Predator {
    static volatile AtomicInteger count = new AtomicInteger(0);

    static {
        UtilAnimal.putMapAmountAnimal(Bear.class, 7);
        Bear.chanceSuccessfulHunt.put(Horse.class, 50);
        Bear.chanceSuccessfulHunt.put(Deer.class, 50);
//        Bear.chanceSuccessfulHunt.put(Rabbit.class, 60);
//        Bear.chanceSuccessfulHunt.put(Mouse.class, 60);
        Bear.chanceSuccessfulHunt.put(Goat.class, 60);
        Bear.chanceSuccessfulHunt.put(Sheep.class, 60);
        Bear.chanceSuccessfulHunt.put(Boa.class, 30);
        UtilAnimal.putSpeedAnimal(Bear.class, 5);
        UtilAnimal.putAbilityToReproduce(Bear.class, 1);
    }

    public Bear() {
        Bear.count.incrementAndGet();
        this.foodConsumption =30;
        this.degreeOfSaturation = 0;
    }

    public static AtomicInteger getCount() {
        return count;
    }

    public static void setCount(AtomicInteger count) {
        count = count;
    }

    public void decrement() {
        Bear.count.decrementAndGet();
        Predator.count.decrementAndGet();
        Animal.getCount().decrementAndGet();
    }

    @Override
    public Animal madeNewAnimal() {
        return new Bear();
    }
}
