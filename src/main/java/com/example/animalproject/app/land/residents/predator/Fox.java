package com.example.animalproject.app.land.residents.predator;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.residents.Animal;
import com.example.animalproject.app.land.residents.herbivore.Mouse;
import com.example.animalproject.app.land.residents.herbivore.Rabbit;

import java.util.concurrent.atomic.AtomicInteger;

public class Fox extends Predator {

    public static volatile AtomicInteger count = new AtomicInteger(0);

    static {
        UtilAnimal.putMapAmountAnimal(Fox.class, 40);
        chanceSuccessfulHunt.put(Rabbit.class, 50);
        chanceSuccessfulHunt.put(Mouse.class, 50);
        UtilAnimal.putSpeedAnimal(Fox.class, 5);
        UtilAnimal.putAbilityToReproduce(Fox.class, 1);
    }

    public Fox() {
        count.incrementAndGet();
        this.foodConsumption = 2;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal madeNewAnimal() {
        return new Fox();
    }

    public void decrement(){
        Fox.count.decrementAndGet();
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
