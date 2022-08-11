package com.example.animalproject.app.land.resident.predator;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.herbivore.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Boa extends Predator {

    public static volatile AtomicInteger count = new AtomicInteger(0);

    static {
        UtilAnimal.putMapAmountAnimal(Boa.class, 50);
        chanceSuccessfulHunt.put(Caterpillar.class, 80);
        chanceSuccessfulHunt.put(Rabbit.class, 40);
        chanceSuccessfulHunt.put(Mouse.class, 40);
        UtilAnimal.putSpeedAnimal(Boa.class, 1);
        UtilAnimal.putAbilityToReproduce(Boa.class, 2);
    }

    public Boa() {
        Boa.count.incrementAndGet();
        this.foodConsumption = 3;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal madeNewAnimal() {
        return new Boa();
    }

    public void decrement(){
       Boa.count.decrementAndGet();
        Predator.count.decrementAndGet();
        Animal.getCount().decrementAndGet();
    }


    public static AtomicInteger getCount() {
        return count;
    }

    public static void setCount(AtomicInteger count) {
        count = count;
    }
}
