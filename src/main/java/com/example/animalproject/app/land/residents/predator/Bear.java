package com.example.animalproject.app.land.residents.predator;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.residents.Animal;
import com.example.animalproject.app.land.residents.herbivore.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Bear extends Predator {
    static volatile AtomicInteger count = new AtomicInteger(0);

    static {
        UtilAnimal.putMapAmountAnimal(Bear.class, 7);
        Bear.chanceSuccessfulHunt.put(Horse.class, 40);
        Bear.chanceSuccessfulHunt.put(Deer.class, 80);
        Bear.chanceSuccessfulHunt.put(Rabbit.class, 80);
        Bear.chanceSuccessfulHunt.put(Mouse.class, 90);
        Bear.chanceSuccessfulHunt.put(Goat.class, 70);
        Bear.chanceSuccessfulHunt.put(Sheep.class, 70);
        Bear.chanceSuccessfulHunt.put(Boa.class, 70);
        UtilAnimal.putSpeedAnimal(Bear.class, 2);
        UtilAnimal.putAbilityToReproduce(Bear.class, 1);
    }

    public Bear() {
        Bear.count.incrementAndGet();
        this.foodConsumption = 40;
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
