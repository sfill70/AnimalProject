package com.example.animalproject.app.land.residents.predator;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.residents.Animal;
import com.example.animalproject.app.land.residents.herbivore.Mouse;
import com.example.animalproject.app.land.residents.herbivore.Rabbit;

import java.util.concurrent.atomic.AtomicInteger;

public class Eagle extends Predator {
    public static volatile AtomicInteger count = new AtomicInteger(0);

    static {
        UtilAnimal.putMapAmountAnimal(Eagle.class, 30);
        chanceSuccessfulHunt.put(Rabbit.class, 90);
        chanceSuccessfulHunt.put(Mouse.class, 90);
        UtilAnimal.putSpeedAnimal(Eagle.class, 4);
        UtilAnimal.putAbilityToReproduce(Eagle.class, 1);
    }

    public Eagle() {
        Eagle.count.incrementAndGet();
        this.foodConsumption = 1;
        this.degreeOfSaturation = 0;
    }



    @Override
    public Animal madeNewAnimal() {
        return new Eagle();
    }

    public void decrement(){
        Eagle.count.decrementAndGet();
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
